package com.frank.han.ui.repo

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.resMapping
import com.frank.han.databinding.FragmentRepoBinding
import com.frank.han.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * RepoFragment
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : BaseFragment(R.layout.fragment_repo) {

    private var _viewBinding: FragmentRepoBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val args by navArgs<RepoFragmentArgs>()
    private val repoViewModel: RepoViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repoViewModel.repo.observe(viewLifecycleOwner) {
            viewBinding.repoTextView.setResource(
                it.resMapping { repos ->
                    repos.joinToString(
                        separator = "\n",
                        transform = { repo -> "<<${repo.desc}>>: ${repo.isPrivate}" }
                    )
                }
            )
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentRepoBinding.bind(view)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
