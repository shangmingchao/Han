package com.frank.han.ui.article

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.frank.han.App
import com.frank.han.api.wan.WeChatService
import com.frank.han.data.ERROR_CODE_NET_HTTP_EXCEPTION
import com.frank.han.data.ERROR_CODE_NET_SOCKET_TIMEOUT
import com.frank.han.data.ERROR_CODE_NET_UNKNOWN_HOST
import com.frank.han.data.Error
import com.frank.han.data.Loading
import com.frank.han.data.NetError
import com.frank.han.data.Success
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.ArticleRepository
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import com.frank.han.util.MainDispatcherRule
import com.frank.han.util.captureValues
import com.frank.han.util.getWanRetrofit
import com.frank.han.util.mockArticles
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import retrofit2.Call
import retrofit2.mock.Calls.failure
import retrofit2.mock.Calls.response
import retrofit2.mock.MockRetrofit
import retrofit2.mock.NetworkBehavior
import java.io.IOException
import java.lang.Thread.sleep
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.Random
import java.util.concurrent.TimeUnit

/**
 * ArticleViewModel Test
 *
 * @author frank
 * @date 2019/12/18 3:11 PM
 */
@RunWith(RobolectricTestRunner::class)
class ArticleViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val app = ApplicationProvider.getApplicationContext<App>()
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
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remoteSuccess() = runTest {
        call = response(mockArticles)
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(0)
        val articleViewModel =
            ArticleViewModel(app, mainDispatcherRule.testDispatcher, id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Success::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * remoteFailedIOException
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remoteFailedIOException() = runTest {
        call = response(mockArticles)
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        val articleViewModel =
            ArticleViewModel(app, mainDispatcherRule.testDispatcher, id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(this.values[1]).isInstanceOf(Error::class.java)
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * remoteFailedSocketTimeoutException
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remoteFailedSocketTimeoutException() = runTest {
        call = response(mockArticles)
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        behavior.setFailureException(SocketTimeoutException("MockSocketTimeoutException"))
        val articleViewModel =
            ArticleViewModel(app, mainDispatcherRule.testDispatcher, id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(((this.values[1] as Error).errorInfo as NetError).code).isEqualTo(
                ERROR_CODE_NET_SOCKET_TIMEOUT,
            )
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * remoteFailedUnknownHostException
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remoteFailedUnknownHostException() = runTest {
        call = response(mockArticles)
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(100)
        behavior.setErrorPercent(0)
        behavior.setFailureException(UnknownHostException("MockUnknownHostException"))
        val articleViewModel =
            ArticleViewModel(app, mainDispatcherRule.testDispatcher, id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(((this.values[1] as Error).errorInfo as NetError).code).isEqualTo(
                ERROR_CODE_NET_UNKNOWN_HOST,
            )
            assertThat(this.values.size).isEqualTo(2)
        }
    }

    /**
     * remoteError
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun remoteError() = runTest {
        call = failure(IOException("Timeout!"))
        behavior.setDelay(10, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        behavior.setErrorPercent(100)
        val articleViewModel =
            ArticleViewModel(app, mainDispatcherRule.testDispatcher, id, page, ArticleRepository(weChatService))
        articleViewModel.articles.captureValues {
            sleep(50)
            assertThat(this.values[0]).isInstanceOf(Loading::class.java)
            assertThat(((this.values[1] as Error).errorInfo as NetError).code).isEqualTo(
                ERROR_CODE_NET_HTTP_EXCEPTION,
            )
            assertThat(this.values.size).isEqualTo(2)
        }
    }
}
