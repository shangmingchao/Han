package com.frank.han.data.repo

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.frank.han.App
import com.frank.han.api.RepoService
import com.frank.han.data.AppDatabase
import com.frank.han.data.repo.entity.Repo
import com.frank.han.data.user.entity.UserWithRepos
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 *
 * @author frank
 * @date 2019/12/2 7:12 PM
 */
class RepoRepository {

    private val webService = Retrofit.Builder()
        .baseUrl(RepoService.END_POINT)
        .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RepoService::class.java)

    private val dao = AppDatabase.getInstance(App.getInstance().applicationContext).repoDao()

    suspend fun getRemoteRepo(username: String): List<Repo> {
        return webService.listUserRepositories(username)
    }

    suspend fun getLocalRepo(username: String): UserWithRepos {
        return dao.getUserRepos(username)
    }
}
