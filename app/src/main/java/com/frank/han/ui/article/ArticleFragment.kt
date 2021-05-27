package com.frank.han.ui.article

import android.os.Bundle
import com.frank.han.R
import com.frank.han.data.wan.BaseDTO
import com.frank.han.data.wan.wechat.entity.ArticlesDTO
import com.frank.han.databinding.FragmentArticleBinding
import com.frank.han.ui.BaseFragment
import com.frank.han.util.binding
import com.frank.han.util.commonRender
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    private val viewBinding: FragmentArticleBinding by binding(FragmentArticleBinding::bind)
    private val articleViewModel: ArticleViewModel by viewModel { parametersOf("408", 1) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        commonRender(articleViewModel.articles, viewBinding, this::dataBinding)
    }

    /**
     * Bind data
     *
     * Should reduce indent?!
     */
    private fun dataBinding(articles: BaseDTO<ArticlesDTO>, viewBinding: FragmentArticleBinding) {
        val data = articles.data.datas.firstOrNull() ?: return
        viewBinding.apply {
            articleTextView.text =
                resources.getString(R.string.title_and_author, data.title, data.author)
        }
    }
}
