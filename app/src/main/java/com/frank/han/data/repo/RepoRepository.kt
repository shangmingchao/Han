package com.frank.han.data.repo

import androidx.lifecycle.LiveData
import com.frank.han.api.RepoService
import com.frank.han.data.repo.entity.Repo

/**
 *
 *
 * @author frank
 * @date 2019/12/2 7:12 PM
 */
class RepoRepository(
    private val repoService: RepoService,
    private val repoDao: RepoDao
) {

    suspend fun getRemoteRepo(username: String): List<Repo> =
        repoService.listUserRepositories(username)

    fun getLocalRepo(username: String): LiveData<List<Repo>> =
        repoDao.getUserRepos()

    suspend fun saveLocalRepo(repos: List<Repo>) =
        repoDao.saveRepo(repos)
}
