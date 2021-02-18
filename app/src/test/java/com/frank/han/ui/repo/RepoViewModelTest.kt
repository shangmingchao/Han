package com.frank.han.ui.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.frank.han.api.github.RepoService
import com.frank.han.data.Resource
import com.frank.han.data.github.repo.RepoDao
import com.frank.han.data.github.repo.RepoRepository
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.repo.entity.RepoVO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.github.user.entity.UserWithRepos
import com.frank.han.util.MainCoroutineScopeRule
import com.frank.han.util.getGitHubRetrofit
import com.frank.han.util.mockRepo
import com.google.common.truth.Truth.assertThat
import java.lang.Thread.sleep
import java.util.*
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior

/**
 * RepoViewModel Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class RepoViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val behavior = NetworkBehavior.create(Random(2847))
    private val localFailed = object : RepoDao {

        private var dbRepo: UserWithRepos? = null
        private val dbFlow = MutableSharedFlow<UserWithRepos>()

        override suspend fun saveRepo(repo: List<RepoPO>) {
            if (dbRepo?.repos != repo) {
                dbFlow.emit(UserWithRepos(UserPO(1L, "", ""), repo))
            }
            dbRepo = UserWithRepos(UserPO(1L, "", ""), repo)
        }

        override fun getUserRepos(username: String): Flow<UserWithRepos> {
            return dbFlow.also {
                coroutineScope.launch {
                    if (dbRepo != null) {
                        dbFlow.emit(dbRepo!!)
                    }
                }
            }
        }
    }

    private val repoServiceDelegate =
        MockRetrofit.Builder(getGitHubRetrofit()).networkBehavior(behavior).build()
            .create(RepoService::class.java)
    private val repoService: RepoService = object : RepoService {
        override suspend fun listUserRepositories(username: String): List<RepoDTO> {
            return repoServiceDelegate.returning(response(listOf(mockRepo())))
                .listUserRepositories("google")
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
        val repoViewModel =
            RepoViewModel(SavedStateHandle(), "google", RepoRepository(repoService, localFailed))
        assertThat(repoViewModel.repo.value).isNull()
        val observer = Observer<Resource<List<RepoVO>>> {}
        repoViewModel.repo.observeForever(observer)
        sleep(100)
        assertThat(repoViewModel.repo.value).isInstanceOf(Resource.Loading::class.java)
        sleep(2000)
        assertThat(repoViewModel.repo.value).isInstanceOf(Resource.Success::class.java)
        repoViewModel.repo.removeObserver(observer)
    }

    /**
     * localFailedRemoteFailed
     */
    @Test
    fun localFailedRemoteFailed() = coroutineScope.runBlockingTest {
        behavior.setDelay(1000, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        val repoViewModel =
            RepoViewModel(SavedStateHandle(), "google", RepoRepository(repoService, localFailed))
        assertThat(repoViewModel.repo.value).isNull()
        val observer = Observer<Resource<List<RepoVO>>> {}
        repoViewModel.repo.observeForever(observer)
        sleep(100)
        assertThat(repoViewModel.repo.value).isInstanceOf(Resource.Loading::class.java)
        sleep(2000)
        assertThat(repoViewModel.repo.value).isInstanceOf(Resource.Errors::class.java)
        repoViewModel.repo.removeObserver(observer)
    }
}
