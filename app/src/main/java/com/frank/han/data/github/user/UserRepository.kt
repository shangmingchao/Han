package com.frank.han.data.github.user

import com.frank.han.api.github.UserService
import com.frank.han.data.github.user.entity.UserDTO
import com.frank.han.data.github.user.entity.UserPO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 *
 *
 * @author frank
 * @date 2019/12/8 7:47 PM
 */
class UserRepository(
    private val userService: UserService,
    private val userDao: UserDao,
) {

    /**
     * getRemoteUser
     *
     * @param username username
     * @return UserDTO
     */
    suspend fun getRemoteUser(username: String): UserDTO =
        userService.getASingleUser(username)

    /**
     * getLocalUser
     *
     * @param username username
     * @return Flow<UserPO>
     */
    fun getLocalUser(username: String): Flow<UserPO> =
        userDao.getUserById(username).distinctUntilChanged()

    /**
     * saveLocalUser
     *
     * @param user UserPO
     */
    suspend fun saveLocalUser(user: UserPO) =
        userDao.saveUser(user)
}
