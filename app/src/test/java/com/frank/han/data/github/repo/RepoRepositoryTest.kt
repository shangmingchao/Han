package com.frank.han.data.github.repo

import com.frank.han.api.github.RepoService
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.util.MOCK_REPO_NAME
import com.frank.han.util.MOCK_USER_NAME
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.mockRepoDTO
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.mock.Calls
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
class RepoRepositoryTest {

    private val behavior = NetworkBehavior.create(Random(2847))
    private lateinit var repoService: RepoService

    /**
     * create WebService
     */
    @Before
    fun create() {
        val retrofit = MockRetrofit.Builder(getGitHubRetrofit()).networkBehavior(behavior).build()
        val repoServiceDelegate = retrofit.create(RepoService::class.java)
        repoService = object : RepoService {
            override suspend fun listUserRepositories(username: String): List<RepoDTO> {
                return repoServiceDelegate.returning(Calls.response(listOf(mockRepoDTO)))
                    .listUserRepositories(MOCK_USER_NAME)
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
            val repoRepository = RepoRepository(repoService, Mockito.mock(RepoDao::class.java))
            val repos = runBlocking { repoRepository.getRemoteRepo(MOCK_USER_NAME) }
            Truth.assertThat(repos.first().name).isEqualTo(MOCK_REPO_NAME)
        }
        Truth.assertThat(time).isAtLeast(100)
    }
}
