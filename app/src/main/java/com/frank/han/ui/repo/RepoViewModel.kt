package com.frank.han.ui.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.frank.han.data.repo.RepoRepository
import com.frank.han.data.repo.entity.Repo
import kotlinx.coroutines.Dispatchers

/**
 *
 *
 * @author frank
 * @date 2019/12/3 10:31 AM
 */
class RepoViewModel : ViewModel() {

    private val repository = RepoRepository()
    val repo = getRepo("google")

    private fun getRepo(userId: String): LiveData<List<Repo>> = liveData(Dispatchers.IO) {
        val remoteRepo = repository.getRemoteRepo(userId)
        emit(remoteRepo)
    }
}
