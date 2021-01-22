package com.frank.han.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.github.user.entity.UserVO
import com.frank.han.util.ModelMapper.map

/**
 *
 *
 * @author frank
 * @date 2019/12/23 2:09 PM
 */
class UserViewModel(
    private val handle: SavedStateHandle,
    private val username: String,
    private val userRepository: UserRepository
) : ViewModel() {

    val user by lazy { getUser(username) }

    private fun getUser(username: String): LiveData<Resource<UserVO>> = getResource(
        databaseQuery = { userRepository.getLocalUser(username) },
        networkCall = { userRepository.getRemoteUser(username) },
        dpMapping = { map(it) },
        pvMapping = { map(it) },
        saveCallResult = { userRepository.saveLocalUser(it) }
    )
}
