package com.frank.han.ui.repo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.github.repo.entity.RepoVO
import com.frank.han.databinding.FragmentRepoBinding
import com.frank.han.ui.BaseFragment
import com.frank.han.util.binding
import com.frank.han.util.renderPage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * RepoFragment
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : BaseFragment(R.layout.fragment_repo) {

    private val vb: FragmentRepoBinding by binding(FragmentRepoBinding::bind)
    private val args by navArgs<RepoFragmentArgs>()
    private val vm: RepoViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        renderPage(vm.repo, vb, FragmentRepoBinding::dataBinding)
    }
}

private fun FragmentRepoBinding.dataBinding(repos: List<RepoVO>) {
    val repo = repos.firstOrNull() ?: return
    repoTextView.text = repo.desc
    repoTextView.setTextColor(repo.color)
}
