package com.frank.han.ui.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.frank.han.api.wan.WeChatService
import com.frank.han.data.Resource
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.ArticleRepository
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import com.frank.han.util.MainCoroutineScopeRule
import com.frank.han.util.captureValues
import com.frank.han.util.getWanRetrofit
import com.frank.han.util.mockArticles
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.mock.Calls.failure
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.io.IOException
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

    private val id = "1"
    private val page = 1
    private var call: Call<BaseDTO<ArticlesDTO>> = response(mockArticles)

    private val behavior = NetworkBehavior.create(Random(2847))
    private val articleServiceDelegate =
        MockRetrofit.Builder(getWanRetrofit()).networkBehavior(behavior).build()
            .create(WeChatService::class.java)
    private val weChatService: WeChatService = object : WeChatService {
        override suspend fun getArticleList(id: String, page: Int): BaseDTO<ArticlesDTO> {
            return articleServiceDelegate.returning(call).getArticleList(id, page)
        }
    }

    /**
     * remoteSuccess
     */
    @Test
    fun remoteSuccess() = coroutineScope.runBlockingTest {
        call = response(mockArticles)
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val articleViewModel =
            ArticleViewModel(SavedStateHandle(), id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(200)
            assertThat(this.values[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Resource.Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * remoteFailed
     */
    @Test
    fun remoteFailed() = coroutineScope.runBlockingTest {
        call = response(mockArticles)
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val articleViewModel =
            ArticleViewModel(SavedStateHandle(), id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(200)
            assertThat(this.values[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Resource.Errors::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * remoteError
     */
    @Test
    fun remoteError() = coroutineScope.runBlockingTest {
        call = failure(IOException("Timeout!"))
        behavior.setDelay(100, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(100)
        val articleViewModel =
            ArticleViewModel(SavedStateHandle(), id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(200)
            assertThat(this.values[0]).isInstanceOf(Resource.Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Resource.Errors::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }
}
