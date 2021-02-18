package com.frank.han.data.github.repo

import com.frank.han.api.github.RepoService
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
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

    /**
     * getRemoteRepo
     *
     * @param username username
     * @return List<RepoDTO>
     */
    suspend fun getRemoteRepo(username: String): List<RepoDTO> =
        repoService.listUserRepositories(username)

    /**
     * getLocalRepo
     *
     * @param username username
     * @return Flow<List<RepoPO>>
     */
    fun getLocalRepo(username: String): Flow<List<RepoPO>> =
        repoDao.getUserRepos(username).distinctUntilChanged().filterNotNull().map { it.repos }

    /**
     * saveLocalRepo
     *
     * @param repos List<RepoPO>
     */
    suspend fun saveLocalRepo(repos: List<RepoPO>) =
        repoDao.saveRepo(repos)
}
