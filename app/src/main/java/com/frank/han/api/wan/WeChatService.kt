package com.frank.han.api.wan

import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * WeChat article API
 *
 * @see <a href="https://www.wanandroid.com/blog/show/2">WeChat article</a>
 * @author frank
 * @date 2019/12/16 3:26 PM
 */
interface WeChatService {

    /**
     * Get user's article
     *
     * @param id String
     * @param page Int
     * @return UserDTO
     */
    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getArticleList(
        @Path("id") id: String,
        @Path("page") page: Int,
    ): BaseDTO<ArticlesDTO>
}
