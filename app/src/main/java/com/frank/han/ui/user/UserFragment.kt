package com.frank.han.ui.user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.resMapping
import com.frank.han.databinding.FragmentUserBinding
import com.frank.han.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class UserFragment : BaseFragment(R.layout.fragment_user) {

    private var _viewBinding: FragmentUserBinding? = null
    private val viewBinding get() = _viewBinding!!
    private val args by navArgs<UserFragmentArgs>()
    private val userViewModel: UserViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel.user.observe(viewLifecycleOwner) {
            viewBinding.userTextView.setResource(it.resMapping { user -> user.username })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _viewBinding = FragmentUserBinding.bind(view)
    }

    override fun onDestroyView() {
        _viewBinding = null
        super.onDestroyView()
    }
}
