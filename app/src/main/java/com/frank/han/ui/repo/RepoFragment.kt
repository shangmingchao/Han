package com.frank.han.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.frank.han.data.Resource.Errors
import com.frank.han.data.Resource.Loading
import com.frank.han.data.Resource.Success
import com.frank.han.databinding.FragmentRepoBinding
import com.frank.han.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 *
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : BaseFragment() {

    private val args by navArgs<RepoFragmentArgs>()

    private lateinit var binding: FragmentRepoBinding
    private val repoViewModel: RepoViewModel by viewModel { parametersOf(Bundle(), args.username) }

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
        repoViewModel.setStyle("brief")
        repoViewModel.repo.observe(viewLifecycleOwner, Observer { resource ->
            binding.repo.text = when (resource) {
                is Loading -> "Loading"
                is Success -> resource.data.toString()
                is Errors -> resource.message
            }
        })
    }

    fun getStyle() = repoViewModel.getStyle()
}
