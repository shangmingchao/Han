package com.frank.han.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frank.han.App
import com.frank.han.R
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.github.repo.RepoRepository
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.repo.entity.RepoVO

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

    val repo by lazy(LazyThreadSafetyMode.NONE) { getRepo(username) }

    private fun getRepo(username: String): LiveData<Resource<List<RepoVO>>> = getResource(
        databaseQuery = { repoRepository.getLocalRepo(username) },
        networkCall = { repoRepository.getRemoteRepo(username) },
        dpMapping = { it.map { dto -> map(dto) } },
        pvMapping = { it.map { po -> map(po) } },
        saveCallResult = { repoRepository.saveLocalRepo(it) }
    )

    private fun map(dto: RepoDTO): RepoPO {
        return RepoPO(dto.id, dto.name, dto.is_private, dto.owner.id)
    }

    private fun map(po: RepoPO): RepoVO {
        val color = App.instance.resources.getColor(
            if (po.is_private) R.color.colorPrimary else R.color.colorPrimaryDark,
        )
        return RepoVO(po.name, color)
    }
}
