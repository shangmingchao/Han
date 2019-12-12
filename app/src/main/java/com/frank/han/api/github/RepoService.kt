package com.frank.han.api.github

import com.frank.han.data.repo.entity.Repo
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Github API
 *
 * @see <a href="https://developer.github.com/v3/">GitHub REST API v3</a>
 * @author frank
 * @date 2019/12/2 6:20 PM
 */
interface RepoService {

    @GET("users/{username}/repos")
    suspend fun listUserRepositories(@Path("username") username: String): List<Repo>
}
