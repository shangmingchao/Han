package com.frank.han.data.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.frank.han.api.github.RepoService
import com.frank.han.data.repo.entity.RepoDTO
import com.frank.han.data.repo.entity.RepoPO

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

    suspend fun getRemoteRepo(username: String): List<RepoDTO> =
        repoService.listUserRepositories(username)

    fun getLocalRepo(username: String): LiveData<List<RepoPO>> =
        Transformations.map(repoDao.getUserRepos(username)) { it?.repos }

    suspend fun saveLocalRepo(repos: List<RepoPO>) =
        repoDao.saveRepo(repos)
}
