package com.frank.han.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.frank.han.databinding.FragmentMainBinding
import com.frank.han.ui.BaseFragment

/**
 *
 *
 * @author frank
 * @date 2019/12/13 3:16 PM
 */
class MainFragment : BaseFragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.repo.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToRepoFragment("google")
            )
        }
    }
}
