package com.frank.han.ui.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.github.user.entity.UserVO

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

    val user by lazy(LazyThreadSafetyMode.NONE) { getUser(username) }

    private fun getUser(username: String): LiveData<Resource<UserVO>> = getResource(
        databaseQuery = { userRepository.getLocalUser(username) },
        networkCall = { userRepository.getRemoteUser(username) },
        dpMapping = { map(it) },
        pvMapping = { map(it) },
        saveCallResult = { userRepository.saveLocalUser(it) }
    )

    private fun map(dto: UserDTO): UserPO {
        return UserPO(dto.id, dto.login, dto.name, dto.public_repos)
    }

    private fun map(po: UserPO): UserVO {
        return UserVO(po.name, "contributes: ${po.public_repos}")
    }
}
