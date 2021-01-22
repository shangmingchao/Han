package com.frank.han.api.github

import com.frank.han.data.github.user.entity.UserDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * GitHub Users API
 *
 * @see <a href="https://developer.github.com/v3/users/">GitHub Users API v3</a>
 * @author frank
 * @date 2019/12/16 3:26 PM
 */
interface UserService {

    /**
     * Get user's information
     * @param username user's login name
     * @return UserDTO
     */
    @GET("users/{username}")
    suspend fun getASingleUser(@Path("username") username: String): UserDTO
}
