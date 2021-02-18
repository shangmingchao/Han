# Han

[![Build Status](https://travis-ci.org/shangmingchao/Han.svg?branch=master)](https://travis-ci.org/shangmingchao/Han)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/4400af8f75f3446eb4fa9191134988a5)](https://www.codacy.com/manual/shangmingchao/Han?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=shangmingchao/Han&amp;utm_campaign=Badge_Grade)
[![codecov](https://codecov.io/gh/shangmingchao/Han/branch/master/graph/badge.svg)](https://codecov.io/gh/shangmingchao/Han)
[![API](https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=21)

It's a sample. Note that some changes (such as database schema modifications) are not backwards compatible during this alpha period and may cause the app to crash. In this case, please uninstall and re-install the app.

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

```kotlin
class UserFragment : BaseFragment(R.layout.fragment_user) {

    private var _viewBinding: FragmentUserBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val args by navArgs<UserFragmentArgs>()
    private val userViewModel: UserViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel.user.observe(viewLifecycleOwner) {
            viewBinding.userTextView.setResource(it.resMapping { user -> user.username })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentUserBinding.bind(view)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
```

```kotlin
class UserViewModel(
    private val handle: SavedStateHandle,
    private val username: String,
    private val userRepository: UserRepository
) : ViewModel() {

    val user by lazy(LazyThreadSafetyMode.NONE) { getUser(username) }

    private fun getUser(username: String): LiveData<Resource<UserVO>> = getResource(
        databaseQuery = { userRepository.getLocalUser(username) },
        networkCall = { userRepository.getRemoteUser(username) },
        dpMapping = { map(it) },
        pvMapping = { map(it) },
        saveCallResult = { userRepository.saveLocalUser(it) }
    )
}
```

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

## More

Practices: Kotlin, Coroutine, Flow, LiveData, ViewModel, Retrofit, Room, Koin, etc.  
Single Source Of Truth + Data Driven  
See [Android project exercises](https://github.com/shangmingchao/shangmingchao.github.io/blob/master/blog/android_project_exercises.md)  
