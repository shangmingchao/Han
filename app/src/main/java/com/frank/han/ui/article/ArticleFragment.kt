package com.frank.han.ui.article

import android.os.Bundle
import com.frank.han.R
import com.frank.han.data.resMapping
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_article.articleTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    private val articleViewModel: ArticleViewModel by viewModel { parametersOf("408", 1) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        articleViewModel.articles.observe(viewLifecycleOwner) {
            articleTextView.setResource(it.resMapping { articles ->
                articles.data.datas.joinToString(
                    separator = "\n",
                    transform = { article -> "<<${article.title}>> by ${article.author}" })
            })
        }
    }
}
