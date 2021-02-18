package com.frank.han.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.frank.han.api.github.UserService
import com.frank.han.data.Resource
import com.frank.han.data.github.user.UserDao
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.github.user.entity.UserVO
import com.frank.han.util.MainCoroutineScopeRule
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.mockUser
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit

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
    private val localFailed = object : UserDao {

        private var dbUser: UserPO? = null
        private val dbFlow = MutableSharedFlow<UserPO>()

        override suspend fun saveUser(user: UserPO) {
            if (user != dbUser) {
                dbFlow.emit(user)
            }
            dbUser = user
        }

        override fun getUserById(userId: String): Flow<UserPO> {
            return dbFlow.also {
                coroutineScope.launch {
                    if (dbUser != null) {
                        dbFlow.emit(dbUser!!)
                    }
                }
            }
        }

        override fun getUserByName(username: String): Flow<UserPO> {
            return dbFlow.also {
                coroutineScope.launch {
                    if (dbUser != null) {
                        dbFlow.emit(dbUser!!)
                    }
                }
            }
        }
    }

    private val userServiceDelegate =
        MockRetrofit.Builder(getGitHubRetrofit()).networkBehavior(behavior).build()
            .create(UserService::class.java)
    private val remoteSuccess: UserService = object : UserService {
        override suspend fun getASingleUser(username: String): UserDTO {
            return userServiceDelegate.returning(response(mockUser())).getASingleUser("google")
        }
    }

    /**
     * localFailedRemoteSuccess
     */
    @Test
    fun localFailedRemoteSuccess() = coroutineScope.runBlockingTest {
        behavior.setDelay(1000, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), "google", UserRepository(remoteSuccess, localFailed))
        assertThat(userViewModel.user.value).isNull()
        val observer = Observer<Resource<UserVO>> {}
        userViewModel.user.observeForever(observer)
        sleep(100)
        assertThat(userViewModel.user.value).isInstanceOf(Resource.Loading::class.java)
        sleep(2000)
        assertThat(userViewModel.user.value).isInstanceOf(Resource.Success::class.java)
        userViewModel.user.removeObserver(observer)
    }
}
