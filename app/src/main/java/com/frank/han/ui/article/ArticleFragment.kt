package com.frank.han.ui.article

import android.os.Bundle
import android.view.View
import com.frank.han.R
import com.frank.han.data.resMapping
import com.frank.han.databinding.FragmentArticleBinding
import com.frank.han.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class ArticleFragment : BaseFragment(R.layout.fragment_article) {

    private var _viewBinding: FragmentArticleBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val articleViewModel: ArticleViewModel by viewModel { parametersOf("408", 1) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        articleViewModel.articles.observe(viewLifecycleOwner) {
            viewBinding.articleTextView.setResource(
                it.resMapping { articles ->
                    articles.data.datas.joinToString(
                        separator = "\n",
                        transform = { article -> "<<${article.title}>> by ${article.author}" }
                    )
                }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentArticleBinding.bind(view)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
