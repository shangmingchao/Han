package com.frank.han.api

import com.frank.han.data.repo.Repo
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Github API
 *
 * @see <a href="https://developer.github.com/v3/">GitHub REST API v3</a>
 * @author frank
 * @date 2019/12/2 6:20 PM
 */
interface GitHubService {

    @GET("users/{user}/repos")
    suspend fun listRepos(@Path("user") user: String): List<Repo>

    companion object {
        const val END_POINT = "https://api.github.com/"
    }
}
