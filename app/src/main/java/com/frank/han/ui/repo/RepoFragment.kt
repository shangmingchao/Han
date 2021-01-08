package com.frank.han.ui.repo

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.ErrorInfo.NetError
import com.frank.han.data.Resource.Errors
import com.frank.han.data.Resource.Loading
import com.frank.han.data.Resource.Success
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_repo.repoText
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 *
 *
 * @author frank
 * @date 2019/12/5 3:38 PM
 */
class RepoFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_repo
    private val args by navArgs<RepoFragmentArgs>()
    private val repoViewModel: RepoViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        repoViewModel.repo.observe(viewLifecycleOwner) {
            repoText.text = when (it) {
                is Loading -> getString(R.string.loading)
                is Success -> it.data.toString()
                is Errors -> null
            }
        }
    }
}
