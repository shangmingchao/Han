# Han

[![Build Status](https://travis-ci.org/shangmingchao/Han.svg?branch=master)](https://travis-ci.org/shangmingchao/Han)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4400af8f75f3446eb4fa9191134988a5)](https://www.codacy.com/manual/shangmingchao/Han?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shangmingchao/Han&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/shangmingchao/Han/branch/master/graph/badge.svg)](https://codecov.io/gh/shangmingchao/Han)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

It's a sample. Note that some changes (such as database schema modifications) are not backwards compatible during this alpha period and may cause the app to crash. In this case, please uninstall and re-install the app.  

**Single Source Of Truth** + **Data Driven** + **Testing**  

## Getting Started

Download Android Studio 3.6 Beta 5 or the latest version  

```shell
git clone https://github.com/shangmingchao/Han.git && cd Han && chmod +x tools/setup.sh && tools/setup.sh
```

Config your Android Studio:  

Open `Preferences...`/`Settings` -> `Editor` -> `Code Style`  
Click the gear icon and select `Import Scheme...`, choose `Han/tools/codestyle.xml` file  
Open `Editor` -> `File and Code Templates` -> `Includes` -> `File Header`  
Edit the template like this:  

```kotlin
/**
 *
 *
 * @author frank
 * @date ${DATE} ${TIME}
 */
```

Checking the following settings:  

- `Editor` -> `General` -> `Strip trailing spaces on Save`
- `Editor` -> `General` -> `Ensure line feed at end of file on Save`
- `Editor` -> `General` -> `Auto Import -> Optimize imports on the fly`

Open `Build` -> `Generate Signed Bundle/APK...` -> `APK` -> `Create new...` to create a keystore file `han.keystore`, keyAlias is `han`, save to the project's root directory  
Create `keystore.properties` file at the project's root directory, Add `keyPassword` and `storePassword` property of the `han.keystore`  

## Sample

### Fragment Sample

```kotlin
class UserFragment : BaseFragment(R.layout.fragment_user) {

    private val vb by binding(FragmentUserBinding::bind)
    private val args by navArgs<UserFragmentArgs>()
    private val vm: UserViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        renderPage(vm.user, vb, FragmentUserBinding::dataBinding)
    }
}

private fun FragmentUserBinding.dataBinding(user: UserVO) {
    username.text = user.username
    description.text = user.description
}
```

### Fragment Testing

```kotlin
@RunWith(AndroidJUnit4::class)
class UserFragmentTest {

    /**
     * Test UserFragment's event
     */
    @Test
    fun testEvent() {
        launchFragmentInContainer<UserFragment>(bundleOf("username" to "google"))
        onView(withId(R.id.username)).check(matches(withContentDescription(R.string.user)))
        sleep(2000)
        onView(withId(R.id.username)).check(matches(withText("Google")))
    }
}
```

### ViewModel

```kotlin
class UserViewModel(
    private val app: Application,
    private val dispatcher: CoroutineDispatcher,
    private val username: String,
    private val userRepository: UserRepository
) : AndroidViewModel(app) {

    val user by lazy(LazyThreadSafetyMode.NONE) { getUser(username) }

    private fun getUser(username: String): LiveData<Resource<UserVO>> = getResource(
        dispatcher = dispatcher,
        databaseQuery = { userRepository.getLocalUser(username) },
        networkCall = { userRepository.getRemoteUser(username) },
        dpMapping = { map(it) },
        pvMapping = { map(it) },
        saveCallResult = { userRepository.saveLocalUser(it) }
    )

    private fun map(dto: UserDTO): UserPO {
        return UserPO(dto.id, dto.login, dto.name, dto.public_repos)
    }

    private fun map(po: UserPO): UserVO {
        val description = app.resources.getString(
            R.string.contributes_desc, po.public_repos
        )
        return UserVO(po.name, description)
    }
}
```

### ViewModel Testing

```kotlin
@RunWith(RobolectricTestRunner::class)
class UserViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val app = RuntimeEnvironment.application as App
    private val testDispatcher = TestCoroutineDispatcher()
    private val behavior = NetworkBehavior.create(Random(2847))
    private var dbUser: UserPO? = null
    private var dbQueryException: Exception? = null
    private var dbSaveException: Exception? = null

    private val userDao = object : UserDao {

        val observerChannel = Channel<Unit>(Channel.CONFLATED)
        lateinit var dbFlow: Flow<UserPO>

        override suspend fun saveUser(user: UserPO) {
            dbSaveException?.let { throw it }
            dbUser = user
            observerChannel.offer(Unit)
        }

        override fun getUserById(userId: String): Flow<UserPO> {
            dbFlow = mockUserFlow()
            return dbFlow
        }

        override fun getUserByName(username: String): Flow<UserPO> {
            dbFlow = mockUserFlow()
            return dbFlow
        }

        private fun mockUserFlow(): Flow<UserPO> {
            return flow {
                observerChannel.offer(Unit)
                withContext(coroutineContext) {
                    try {
                        for (signal in observerChannel) {
                            withContext(coroutineContext) {
                                dbQueryException?.let { throw it }
                                dbUser?.let { emit(it) }
                            }
                        }
                    } finally {
                        // ignore
                    }
                }
            }
        }
    }

    private val userServiceDelegate =
        MockRetrofit.Builder(getGitHubRetrofit()).networkBehavior(behavior).build()
            .create(UserService::class.java)
    private val userService: UserService = object : UserService {
        override suspend fun getASingleUser(username: String): UserDTO {
            return userServiceDelegate.returning(response(mockUserDTO))
                .getASingleUser(MOCK_USER_NAME)
        }
    }

    /**
     * localFailedRemoteSuccess
     */
    @Test
    fun localFailedRemoteSuccess() = coroutineScope.runBlockingTest {
        dbUser = null
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(app, testDispatcher, MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localFailedRemoteFailed
     */
    @Test
    fun localFailedRemoteFailed() = coroutineScope.runBlockingTest {
        dbUser = null
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(app, testDispatcher, MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Error::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localSuccessRemoteSuccess
     */
    @Test
    fun localSuccessRemoteSuccess() = coroutineScope.runBlockingTest {
        dbUser = mockUserPO
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(app, testDispatcher, MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localSuccessRemoteFailed
     */
    @Test
    fun localSuccessRemoteFailed() = coroutineScope.runBlockingTest {
        dbUser = mockUserPO
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(app, testDispatcher, MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values[2]).isInstanceOf(Error::class.java)
            assertThat(this.values[3]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(4)
        }
    }

    /**
     * localQueryExceptionRemoteSuccess
     *
     * Note: Flow will not work if databaseQuery throws an exception. So the further saveCallResult will not be signaled.
     * It's a bug?!
     */
    @Test
    fun localQueryExceptionRemoteSuccess() = coroutineScope.runBlockingTest {
        dbQueryException = SQLiteReadOnlyDatabaseException("MockSQLiteReadOnlyDatabaseException!")
        dbUser = null
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(app, testDispatcher, MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(((this.values[1] as Error).errorInfo as DBError).e).isInstanceOf(
                SQLiteReadOnlyDatabaseException::class.java
            )
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localSaveExceptionRemoteSuccess
     *
     * User will not be signaled if saveResult failed
     */
    @Test
    fun localSaveExceptionRemoteSuccess() = coroutineScope.runBlockingTest {
        dbSaveException = SQLiteOutOfMemoryException("MockSQLiteOutOfMemoryException!")
        dbUser = mockUserPO
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(app, testDispatcher, MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }
}
```

### Repository

```kotlin
class UserRepository(
    private val userService: UserService,
    private val userDao: UserDao
) {

    suspend fun getRemoteUser(username: String): UserDTO =
            userService.getASingleUser(username)
    
    fun getLocalUser(username: String): Flow<UserPO> =
            userDao.getUserByName(username).distinctUntilChanged()

    suspend fun saveLocalUser(user: UserPO) =
            userDao.saveUser(user)
}
```

### Repository Testing

```kotlin
class UserRepositoryTest {

    private val behavior = NetworkBehavior.create(Random(2847))
    private lateinit var userService: UserService

    /**
     * create WebService
     */
    @Before
    fun create() {
        val retrofit = MockRetrofit.Builder(getGitHubRetrofit()).networkBehavior(behavior).build()
        val userServiceDelegate = retrofit.create(UserService::class.java)
        userService = object : UserService {
            override suspend fun getASingleUser(username: String): UserDTO {
                return userServiceDelegate.returning(response(mockUserDTO))
                    .getASingleUser(MOCK_USER_NAME)
            }
        }
    }

    /**
     * testService
     */
    @Test
    fun testService() = runBlocking {
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        val time = measureTimeMillis {
            val userRepository = UserRepository(userService, mock(UserDao::class.java))
            val user = runBlocking { userRepository.getRemoteUser(MOCK_USER_NAME) }
            assertThat(user.login).isEqualTo(MOCK_USER_LOGIN)
        }
        assertThat(time).isAtLeast(100)
    }
}
```

### Database Testing

```kotlin
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    val dbRule = AppDatabaseRule()

    /**
     * Test UserDao
     *
     * @throws Exception
     */
    @Test
    @Throws(Exception::class)
    fun testUser() = runBlocking {
        val userDao = dbRule.db.userDao()
        val user = mockedUserPO
        userDao.saveUser(user)
        val idUser = userDao.getUserById(MOCKED_USER_LOGIN).first()
        assertThat(idUser).isEqualTo(user)
        val nameUser = userDao.getUserByName(MOCKED_USER_NAME).first()
        assertThat(nameUser).isEqualTo(user)
    }
}
```

## More

See [Android project exercises](https://github.com/shangmingchao/shangmingchao.github.io/blob/master/blog/android_project_exercises.md)  
