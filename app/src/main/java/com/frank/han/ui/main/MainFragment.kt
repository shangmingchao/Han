package com.frank.han.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.frank.han.R
import com.frank.han.data.app.AppPrefs
import com.frank.han.ui.BaseFragment
import com.frank.han.ui.article.ArticleActivity
import kotlinx.android.synthetic.main.fragment_main.articleBtn
import kotlinx.android.synthetic.main.fragment_main.counterText
import kotlinx.android.synthetic.main.fragment_main.repoBtn
import kotlinx.android.synthetic.main.fragment_main.userBtn
import kotlinx.coroutines.Dispatchers
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

    private val appPrefs: AppPrefs by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userBtn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                appPrefs.increaseCounter()
            }
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToUserFragment("google")
            )
        }
        repoBtn.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToRepoFragment("google")
            )
        }
        articleBtn.setOnClickListener {
            startActivity(Intent(activity, ArticleActivity::class.java))
        }
        appPrefs.getCounter().asLiveData().observe(viewLifecycleOwner) {
            counterText.text = format(getString(R.string.user_click), "$it")
        }
    }
}
