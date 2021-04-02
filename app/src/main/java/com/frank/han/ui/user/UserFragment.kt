package com.frank.han.ui.user

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.github.user.entity.UserVO
import com.frank.han.databinding.FragmentUserBinding
import com.frank.han.ui.BaseFragment
import com.frank.han.util.bindData
import com.frank.han.util.binding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class UserFragment : BaseFragment(R.layout.fragment_user) {

    private val viewBinding by binding(FragmentUserBinding::bind)
    private val args by navArgs<UserFragmentArgs>()
    private val userViewModel: UserViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindData(userViewModel.user, viewBinding, this::dataBinding)
    }

    /**
     * Bind data
     *
     * Should reduce indent?!
     */
    private fun dataBinding(user: UserVO, viewBinding: FragmentUserBinding) {
        viewBinding.apply {
            username.text = user.username
            description.text = user.description
        }
    }
}
