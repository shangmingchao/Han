package com.frank.han.ui.main

import android.os.Bundle
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import com.frank.han.R
import com.frank.han.data.app.AppPrefs
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*
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
        val counter: LiveData<Int> = liveData {
            emitSource(appPrefs.getCounter().asLiveData())
        }
        counter.observe(viewLifecycleOwner, Observer { counterText.text = "$it" })
    }
}
