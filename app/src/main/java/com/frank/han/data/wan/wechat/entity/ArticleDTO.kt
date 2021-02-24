package com.frank.han.data.wan.wechat.entity

/**
 * ArticlesDTO
 *
 * @author frank
 * @date 2019/12/18 2:37 PM
 */
data class ArticlesDTO(
    val curPage: Int,
    val datas: List<ArticleDTO>,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int
)

/**
 * ArticleDTO
 */
data class ArticleDTO(
    val author: String,
    val title: String,
)
