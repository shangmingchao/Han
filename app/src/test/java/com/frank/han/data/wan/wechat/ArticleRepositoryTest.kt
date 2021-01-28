package com.frank.han.data.wan.wechat

import com.frank.han.api.wan.WeChatService
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import com.frank.han.util.getWanRetrofit
import com.frank.han.util.mockArticles
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
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
class ArticleRepositoryTest {

    private val behavior = NetworkBehavior.create(Random(2847))
    private lateinit var articleService: WeChatService

    @Before
    fun create() {
        val retrofit = MockRetrofit.Builder(getWanRetrofit()).networkBehavior(behavior).build()
        val articleServiceDelegate = retrofit.create(WeChatService::class.java)
        articleService = object : WeChatService {
            override suspend fun getArticleList(id: String, page: Int): BaseDTO<ArticlesDTO> {
                return articleServiceDelegate.returning(Calls.response(mockArticles()))
                    .getArticleList("1", 1)
            }
        }
    }

    @Test
    fun testService() = runBlocking {
        behavior.setDelay(1000, TimeUnit.MILLISECONDS)
        behavior.setVariancePercent(0)
        behavior.setFailurePercent(0)
        val time = measureTimeMillis {
            val articleRepository = ArticleRepository(articleService)
            val articles = runBlocking { articleRepository.getArticleList("1", 1) }
            assertThat(articles.data.datas.first().title).isEqualTo("title1")
        }
        assertThat(time).isAtLeast(1000)
    }
}
