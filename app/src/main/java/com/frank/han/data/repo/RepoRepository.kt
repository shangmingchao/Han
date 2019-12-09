package com.frank.han.data.repo

import androidx.lifecycle.LiveData
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.frank.han.App
import com.frank.han.BuildConfig
import com.frank.han.api.RepoService
import com.frank.han.data.AppDatabase
import com.frank.han.data.repo.entity.Repo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
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
        .client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) BASIC else NONE
                    })
                .addNetworkInterceptor(StethoInterceptor())
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RepoService::class.java)

    private val dao = AppDatabase.getInstance(App.getInstance().applicationContext).repoDao()

    suspend fun getRemoteRepo(username: String): List<Repo> =
        webService.listUserRepositories(username)

    fun getLocalRepo(username: String): LiveData<List<Repo>> =
        dao.getUserRepos()

    suspend fun saveLocalRepo(repos: List<Repo>) =
        dao.saveRepo(repos)
}
