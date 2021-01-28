package com.frank.han.ui.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.frank.han.api.github.UserService
import com.frank.han.data.github.user.UserDao
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.util.MainCoroutineScopeRule
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.getValueForTest
import com.frank.han.util.mockUser
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Rule
import org.junit.Test
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
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
        override suspend fun saveUser(user: UserPO) {
            // ignore
        }

        override fun getUserById(userId: String): Flow<UserPO> {
            return flowOf(UserPO(1L, "", ""))
        }

        override fun getUserByName(username: String): Flow<UserPO> {
            return flowOf(UserPO(1L, "", ""))
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

    @Test
    fun localFailedRemoteSuccess() {
        behavior.setDelay(1000, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        val userViewModel =
            UserViewModel(SavedStateHandle(), "google", UserRepository(remoteSuccess, localFailed))
        assertThat(userViewModel.user.getValueForTest()).isNull()
    }
}
