package com.frank.han.data.user

import androidx.lifecycle.LiveData
import com.frank.han.api.github.UserService
import com.frank.han.data.user.entity.UserDTO
import com.frank.han.data.user.entity.UserPO

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:47 PM
 */
class UserRepository(
    private val userService: UserService,
    private val userDao: UserDao
) {

    suspend fun getRemoteUser(username: String): UserDTO =
        userService.getASingleUser(username)

    fun getLocalUser(username: String): LiveData<UserPO> =
        userDao.getUserByName(username)

    suspend fun saveLocalUser(user: UserPO) =
        userDao.saveUser(user)
}
