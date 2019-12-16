package com.frank.han.api.github

import com.frank.han.data.user.entity.User
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

    @GET("/users/{username}")
    suspend fun getASingleUser(@Path("username") username: String): User
}
