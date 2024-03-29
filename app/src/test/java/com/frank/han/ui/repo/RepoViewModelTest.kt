package com.frank.han.ui.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.frank.han.App
import com.frank.han.api.github.RepoService
import com.frank.han.data.Error
import com.frank.han.data.Loading
import com.frank.han.data.Success
import com.frank.han.data.github.repo.RepoDao
import com.frank.han.data.github.repo.RepoRepository
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.user.entity.UserWithRepos
import com.frank.han.util.MOCK_USER_NAME
import com.frank.han.util.MainDispatcherRule
import com.frank.han.util.captureValues
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.mockRepoDTO
import com.frank.han.util.mockRepoPO
import com.frank.han.util.mockUserPO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.lang.Thread.sleep
import java.util.Random
import java.util.concurrent.TimeUnit

/**
 * RepoViewModel Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
@RunWith(RobolectricTestRunner::class)
class RepoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val app = ApplicationProvider.getApplicationContext<App>()
    private val behavior = NetworkBehavior.create(Random(2847))
    private var dbRepo: UserWithRepos? = null
    private val repoDao = object : RepoDao {

        val observerChannel = Channel<Unit>(Channel.CONFLATED)
        lateinit var dbFlow: Flow<UserWithRepos>

        override suspend fun saveRepo(repo: List<RepoPO>) {
            dbRepo = UserWithRepos(mockUserPO, repo)
            observerChannel.trySend(Unit).isSuccess
        }

        override fun getUserRepos(username: String): Flow<UserWithRepos> {
            dbFlow = flow {
                observerChannel.trySend(Unit).isSuccess
                for (signal in observerChannel) {
                    dbRepo?.let { emit(it) }
                }
            }
            return dbFlow
        }
    }

    private val repoServiceDelegate =
        MockRetrofit.Builder(getGitHubRetrofit()).networkBehavior(behavior).build()
            .create(RepoService::class.java)
    private val repoService: RepoService = object : RepoService {
        override suspend fun listUserRepositories(username: String): List<RepoDTO> {
            return repoServiceDelegate.returning(response(listOf(mockRepoDTO)))
                .listUserRepositories(MOCK_USER_NAME)
        }
    }

    /**
     * localFailedRemoteSuccess
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun localFailedRemoteSuccess() = runTest {
        dbRepo = null
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val repoViewModel =
            RepoViewModel(app, mainDispatcherRule.testDispatcher, MOCK_USER_NAME, RepoRepository(repoService, repoDao))
        repoViewModel.repo.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localFailedRemoteFailed
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun localFailedRemoteFailed() = runTest {
        dbRepo = null
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val repoViewModel =
            RepoViewModel(app, mainDispatcherRule.testDispatcher, MOCK_USER_NAME, RepoRepository(repoService, repoDao))
        repoViewModel.repo.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Error::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localSuccessRemoteSuccess
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun localSuccessRemoteSuccess() = runTest {
        dbRepo = UserWithRepos(mockUserPO, listOf(mockRepoPO))
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val repoViewModel =
            RepoViewModel(app, mainDispatcherRule.testDispatcher, MOCK_USER_NAME, RepoRepository(repoService, repoDao))
        repoViewModel.repo.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * localSuccessRemoteFailed
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun localSuccessRemoteFailed() = runTest {
        dbRepo = UserWithRepos(mockUserPO, listOf(mockRepoPO))
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val repoViewModel =
            RepoViewModel(app, mainDispatcherRule.testDispatcher, MOCK_USER_NAME, RepoRepository(repoService, repoDao))
        repoViewModel.repo.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values[2]).isInstanceOf(Error::class.java)
            assertThat(this.values[3]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(4)
        }
    }
}
