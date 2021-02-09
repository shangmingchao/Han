package com.frank.han.data.wan.wechat

import com.frank.han.api.wan.WeChatService
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO

/**
 * ArticleRepository
 *
 * @author frank
 * @date 2019/12/8 7:47 PM
 */
class ArticleRepository(private val weChatService: WeChatService) {

    /**
     * Get article's list
     *
     * @param id user's id
     * @param page page
     * @return article's list
     */
    suspend fun getArticleList(id: String, page: Int): BaseDTO<ArticlesDTO> =
        weChatService.getArticleList(id, page)
}
