package com.frank.han.ui.repo

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.resMapping
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_repo.repoTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * RepoFragment
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : BaseFragment(R.layout.fragment_repo) {

    private val args by navArgs<RepoFragmentArgs>()
    private val repoViewModel: RepoViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repoViewModel.repo.observe(viewLifecycleOwner) {
            repoTextView.setResource(it.resMapping { repos ->
                repos.joinToString(
                    separator = "\n",
                    transform = { repo -> "<<${repo.desc}>>: ${repo.isPrivate}" })
            })
        }
    }
}
