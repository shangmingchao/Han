package com.frank.han.ui.user

import android.database.sqlite.SQLiteOutOfMemoryException
import android.database.sqlite.SQLiteReadOnlyDatabaseException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.frank.han.api.github.UserService
import com.frank.han.data.DBError
import com.frank.han.data.Error
import com.frank.han.data.Loading
import com.frank.han.data.Success
import com.frank.han.data.github.user.UserDao
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.util.MOCK_USER_NAME
import com.frank.han.util.MainCoroutineScopeRule
import com.frank.han.util.captureValues
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.mockUserDTO
import com.frank.han.util.mockUserPO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.coroutines.coroutineContext

/**
 * UserViewModel Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class UserViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

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
            return flow { }
        }

        override fun getUserByName(username: String): Flow<UserPO> {
            dbFlow = flow {
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
            return dbFlow
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
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(200)
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
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(200)
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
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(200)
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
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(200)
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
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(200)
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
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), MOCK_USER_NAME, UserRepository(userService, userDao))
        userViewModel.user.captureValues {
            sleep(200)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }
}
