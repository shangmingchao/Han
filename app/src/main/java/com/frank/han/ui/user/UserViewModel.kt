package com.frank.han.ui.user

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.frank.han.R
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.data.github.user.entity.UserVO
import kotlinx.coroutines.CoroutineDispatcher

/**
 * UserViewModel
 *
 * @author frank
 * @date 2019/12/23 2:09 PM
 */
class UserViewModel(
    private val app: Application,
    private val dispatcher: CoroutineDispatcher,
    private val username: String,
    private val userRepository: UserRepository,
) : AndroidViewModel(app) {

    val user by lazy(LazyThreadSafetyMode.NONE) { getUser(username) }

    private fun getUser(username: String): LiveData<Resource<UserVO>> = getResource(
        dispatcher = dispatcher,
        databaseQuery = { userRepository.getLocalUser(username) },
        networkCall = { userRepository.getRemoteUser(username) },
        dpMapping = { map(it) },
        pvMapping = { map(it) },
        saveCallResult = { userRepository.saveLocalUser(it) },
    )

    private fun map(dto: UserDTO): UserPO {
        return UserPO(dto.id, dto.login, dto.name, dto.public_repos)
    }

    private fun map(po: UserPO): UserVO {
        val description = app.resources.getString(
            R.string.contributes_desc,
            po.public_repos,
        )
        return UserVO(po.name, description)
    }
}
