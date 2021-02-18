package com.frank.han.ui.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import com.frank.han.api.wan.WeChatService
import com.frank.han.data.Resource
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.ArticleRepository
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import com.frank.han.util.MainCoroutineScopeRule
import com.frank.han.util.getWanRetrofit
import com.frank.han.util.mockArticles
import com.google.common.truth.Truth.assertThat
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
 * ArticleViewModel Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
class ArticleViewModelTest {

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val behavior = NetworkBehavior.create(Random(2847))
    private val articleServiceDelegate =
        MockRetrofit.Builder(getWanRetrofit()).networkBehavior(behavior).build()
            .create(WeChatService::class.java)
    private val remoteSuccess: WeChatService = object : WeChatService {
        override suspend fun getArticleList(id: String, page: Int): BaseDTO<ArticlesDTO> {
            return articleServiceDelegate.returning(response(mockArticles())).getArticleList("1", 1)
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
        val articleViewModel =
            ArticleViewModel(SavedStateHandle(), "", 1, ArticleRepository(remoteSuccess))
        assertThat(articleViewModel.articles.value).isNull()
        val observer = Observer<Resource<BaseDTO<ArticlesDTO>>> {}
        articleViewModel.articles.observeForever(observer)
        sleep(100)
        assertThat(articleViewModel.articles.value).isInstanceOf(Resource.Loading::class.java)
        sleep(2000)
        assertThat(articleViewModel.articles.value).isInstanceOf(Resource.Success::class.java)
        articleViewModel.articles.removeObserver(observer)
    }
}
