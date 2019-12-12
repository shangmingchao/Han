package com.frank.han.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.frank.han.data.Resource
import com.frank.han.data.getResource
import com.frank.han.data.repo.RepoRepository
import com.frank.han.data.repo.entity.Repo

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

    private fun getRepo(username: String): LiveData<Resource<List<Repo>>> = getResource(
        databaseQuery = { repoRepository.getLocalRepo(username) },
        networkCall = { repoRepository.getRemoteRepo(username) },
        saveCallResult = { repoRepository.saveLocalRepo(it) }
    )

    fun getStyle(): String? = handle.get<String>(STYLE_KEY)

    fun setStyle(style: String) = handle.set(STYLE_KEY, style)

    companion object {
        const val STYLE_KEY = "style"
    }
}
