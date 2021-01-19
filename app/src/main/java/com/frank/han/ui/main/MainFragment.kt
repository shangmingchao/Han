package com.frank.han.ui.main

import android.os.Bundle
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.frank.han.R
import com.frank.han.data.app.AppPrefs
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.counterText
import kotlinx.android.synthetic.main.fragment_main.repoBtn
import kotlinx.android.synthetic.main.fragment_main.userBtn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

/**
 *
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
class MainFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_main
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
        appPrefs.getCounter().asLiveData().observe(viewLifecycleOwner) {
            counterText.text = "$it"
        }
    }

    fun getCounter(): Int {
        return try {
            counterText.text.toString().toInt()
        } catch (e: Exception) {
            0
        }
    }
}
