package com.frank.han.ui.user

import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.Resource.Errors
import com.frank.han.data.Resource.Loading
import com.frank.han.data.Resource.Success
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_user.userTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * UserFragment
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class UserFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_user
    private val args by navArgs<UserFragmentArgs>()
    private val userViewModel: UserViewModel by viewModel { parametersOf(args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel.user.observe(viewLifecycleOwner) {
            userTextView.text = when (it) {
                is Loading -> getString(R.string.loading)
                is Success -> it.data?.username
                is Errors -> getString(R.string.empty_text)
            }
        }
    }
}
