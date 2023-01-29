package com.frank.han.ui.user

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.github.user.entity.UserVO
import com.frank.han.databinding.FragmentUserBinding
import com.frank.han.ui.BaseFragment
import com.frank.han.util.binding
import com.frank.han.util.renderPage
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class UserFragment : BaseFragment(R.layout.fragment_user) {

    private val vb by binding(FragmentUserBinding::bind)
    private val args by navArgs<UserFragmentArgs>()
    private val vm: UserViewModel by viewModel { parametersOf(args.username) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderPage(vm.user, vb, FragmentUserBinding::dataBinding)
    }
}

private fun FragmentUserBinding.dataBinding(user: UserVO) {
    username.text = user.username
    description.text = user.description
}
