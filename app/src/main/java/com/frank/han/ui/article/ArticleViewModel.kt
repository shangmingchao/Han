package com.frank.han.ui.article

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.frank.han.data.Resource
import com.frank.han.data.getNetResource
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.ArticleRepository
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import kotlinx.coroutines.CoroutineDispatcher

/**
 * ArticleViewModel
 *
 * @author frank
 * @date 2019/12/23 2:09 PM
 */
class ArticleViewModel(
    app: Application,
    private val dispatcher: CoroutineDispatcher,
    private val id: String,
    private val page: Int,
    private val articleRepository: ArticleRepository,
) : AndroidViewModel(app) {

    val articles by lazy(LazyThreadSafetyMode.NONE) { getArticleList() }

    private fun getArticleList(): LiveData<Resource<BaseDTO<ArticlesDTO>>> =
        getNetResource(dispatcher) { articleRepository.getArticleList(id, page) }
}
