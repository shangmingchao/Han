package com.frank.han.ui.main

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.frank.han.R
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.repoBtn

/**
 *
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
class MainFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_main

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repoBtn.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToRepoFragment("google")
            )
        }
    }
}
