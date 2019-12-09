package com.frank.han.ui.repo

import androidx.lifecycle.LiveData
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
class RepoViewModel : ViewModel() {

    private val repository = RepoRepository()
    val repo = getRepo("google")

    private fun getRepo(username: String): LiveData<Resource<List<Repo>>> = getResource(
        databaseQuery = { repository.getLocalRepo(username) },
        networkCall = { repository.getRemoteRepo(username) },
        saveCallResult = { repository.saveLocalRepo(it) }
    )
}
