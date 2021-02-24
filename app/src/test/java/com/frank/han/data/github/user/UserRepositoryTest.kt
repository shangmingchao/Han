package com.frank.han.data.github.user

import com.frank.han.api.github.UserService
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.util.MOCK_USER_LOGIN
import com.frank.han.util.MOCK_USER_NAME
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.mockUserDTO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.system.measureTimeMillis

/**
 * ModelMapper Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
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
