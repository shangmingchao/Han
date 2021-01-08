package com.frank.han.data.repo

import com.frank.han.api.github.RepoService
import com.frank.han.data.repo.entity.RepoDTO
import com.frank.han.data.repo.entity.RepoPO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

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

    fun getLocalRepo(username: String): Flow<List<RepoPO>> =
            repoDao.getUserRepos(username).distinctUntilChanged().map { it?.repos }

    suspend fun saveLocalRepo(repos: List<RepoPO>) =
            repoDao.saveRepo(repos)
}
