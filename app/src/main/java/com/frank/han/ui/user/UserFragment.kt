package com.frank.han.ui.user

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.frank.han.R
import com.frank.han.data.ErrorInfo.NetError
import com.frank.han.data.Resource.Errors
import com.frank.han.data.Resource.Loading
import com.frank.han.data.Resource.Success
import com.frank.han.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_user.userTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 *
 *
 * @author frank
 * @date 2019/12/23 2:08 PM
 */
class UserFragment : BaseFragment() {

    override val layoutId = R.layout.fragment_user
    private val args by navArgs<UserFragmentArgs>()
    private val userViewModel: UserViewModel by viewModel { parametersOf(Bundle(), args.username) }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        userViewModel.user.observe(viewLifecycleOwner, Observer { resource ->
            Log.e("aaaa", "observe:")
            userTextView.text = when (resource) {
                is Loading -> getString(R.string.loading)
                is Success -> resource.data.toString()
                is Errors -> {
                    if (resource.errorInfo is NetError) {
                        Toast.makeText(context, R.string.net_error, Toast.LENGTH_SHORT).show()
                    }
                    null
                }
            }
        })
    }
}
