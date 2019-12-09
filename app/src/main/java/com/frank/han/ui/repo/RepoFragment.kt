package com.frank.han.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.frank.han.data.Resource.Errors
import com.frank.han.data.Resource.Loading
import com.frank.han.data.Resource.Success
import com.frank.han.databinding.FragmentRepoBinding

/**
 *
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : Fragment() {

    private lateinit var binding: FragmentRepoBinding
    private val viewModel: RepoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.repo.observe(viewLifecycleOwner, Observer { resource ->
            binding.repo.text = when (resource) {
                is Loading -> "Loading"
                is Success -> resource.data.toString()
                is Errors -> resource.message
            }
        })
    }
}
