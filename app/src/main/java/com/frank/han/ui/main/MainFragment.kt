package com.frank.han.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.frank.han.R
import com.frank.han.data.app.AppPrefs
import com.frank.han.databinding.FragmentMainBinding
import com.frank.han.ui.BaseFragment
import com.frank.han.ui.article.ArticleActivity
import com.frank.han.ui.article.ArticleDetailActivity
import com.frank.han.util.binding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import okhttp3.internal.format
import org.koin.android.ext.android.inject

/**
 * MainFragment
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
class MainFragment : BaseFragment(R.layout.fragment_main) {

    private val viewBinding by binding(FragmentMainBinding::bind)
    private val dispatcher by inject<CoroutineDispatcher>()
    private val appPrefs: AppPrefs by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.userBtn.setOnClickListener {
            lifecycleScope.launch(dispatcher) {
                appPrefs.increaseCounter()
            }
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToUserFragment("google"),
            )
        }
        viewBinding.repoBtn.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToRepoFragment("google"),
            )
        }
        viewBinding.articleBtn.setOnClickListener {
            startActivity(Intent(activity, ArticleActivity::class.java))
        }
        viewBinding.articleDetailBtn.setOnClickListener {
            startActivity(Intent(activity, ArticleDetailActivity::class.java))
        }
        appPrefs.getCounter().asLiveData().observe(viewLifecycleOwner) {
            viewBinding.counterText.text = format(getString(R.string.user_click), "$it")
        }
    }
}
