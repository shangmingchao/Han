package com.frank.han.ui.repo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.frank.han.R
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.github.repo.RepoRepository
import com.frank.han.data.github.repo.entity.RepoDTO
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.repo.entity.RepoVO
import kotlinx.coroutines.CoroutineDispatcher

/**
 *
 *
 * @author frank
 * @date 2019/12/3 10:31 AM
 */
class RepoViewModel(
    private val app: Application,
    private val dispatcher: CoroutineDispatcher,
    private val username: String,
    private val repoRepository: RepoRepository
) : AndroidViewModel(app) {

    val repo by lazy(LazyThreadSafetyMode.NONE) { getRepo(username) }

    private fun getRepo(username: String): LiveData<Resource<List<RepoVO>>> = getResource(
        dispatcher = dispatcher,
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
        val color = app.resources.getColor(
            if (po.is_private) R.color.colorPrimary else R.color.colorPrimaryDark,
        )
        return RepoVO(po.name, color)
    }
}
