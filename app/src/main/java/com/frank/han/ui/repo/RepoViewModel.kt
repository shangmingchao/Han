package com.frank.han.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.github.repo.RepoRepository
import com.frank.han.data.github.repo.entity.RepoVO
import com.frank.han.util.ModelMapper.map

/**
 *
 *
 * @author frank
 * @date 2019/12/3 10:31 AM
 */
class RepoViewModel(
    private val handle: SavedStateHandle,
    private val username: String,
    private val repoRepository: RepoRepository
) : ViewModel() {

    val repo by lazy { getRepo(username) }

    private fun getRepo(username: String): LiveData<Resource<List<RepoVO>>> = getResource(
        databaseQuery = { repoRepository.getLocalRepo(username) },
        networkCall = { repoRepository.getRemoteRepo(username) },
        dpMapping = { it.map { dto -> map(dto) } },
        pvMapping = { it.map { po -> map(po) } },
        saveCallResult = { repoRepository.saveLocalRepo(it) }
    )
}
