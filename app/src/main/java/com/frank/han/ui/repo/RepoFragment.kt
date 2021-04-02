package com.frank.han.ui.repo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.github.repo.entity.RepoVO
import com.frank.han.databinding.FragmentRepoBinding
import com.frank.han.ui.BaseFragment
import com.frank.han.util.bindData
import com.frank.han.util.binding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * RepoFragment
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : BaseFragment(R.layout.fragment_repo) {

    private val viewBinding: FragmentRepoBinding by binding(FragmentRepoBinding::bind)
    private val args by navArgs<RepoFragmentArgs>()
    private val repoViewModel: RepoViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindData(repoViewModel.repo, viewBinding, this::dataBinding)
    }

    /**
     * Bind data
     *
     * Should reduce indent?!
     */
    private fun dataBinding(repos: List<RepoVO>, viewBinding: FragmentRepoBinding) {
        viewBinding.apply {
            val repo = repos.firstOrNull() ?: return@apply
            repoTextView.text = repo.desc
            repoTextView.setTextColor(repo.color)
        }
    }
}
