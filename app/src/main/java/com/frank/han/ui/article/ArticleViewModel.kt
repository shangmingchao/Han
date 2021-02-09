package com.frank.han.ui.article

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frank.han.data.Resource
import com.frank.han.data.getNetResource
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.ArticleRepository
import com.frank.han.data.wan.wechat.entity.ArticlesDTO

/**
 * ArticleViewModel
 *
 * @author frank
 * @date 2019/12/23 2:09 PM
 */
class ArticleViewModel(
    private val handle: SavedStateHandle,
    private val id: String,
    private val page: Int,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    val articles by lazy(LazyThreadSafetyMode.NONE) { getArticleList() }

    private fun getArticleList(): LiveData<Resource<BaseDTO<ArticlesDTO>>> = getNetResource {
        articleRepository.getArticleList(id, page)
    }
}
