package com.frank.han.api.github

import com.frank.han.data.github.repo.entity.RepoDTO
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Github Repositories API
 *
 * @see <a href="https://developer.github.com/v3/repos/">GitHub Repositories API v3</a>
 * @author frank
 * @date 2019/12/2 6:20 PM
 */
interface RepoService {

    /**
     * List user's repositories
     *
     * @param username user's login name
     * @return List<RepoDTO>
     */
    @GET("users/{username}/repos")
    suspend fun listUserRepositories(@Path("username") username: String): List<RepoDTO>
}
