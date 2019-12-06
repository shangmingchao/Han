package com.frank.han.data.repo

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.frank.han.api.GitHubService
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

    suspend fun getRemoteRepo(user: String): List<Repo> {
        Log.d("TAG", "RepoRepository_getRemoteRepo: ${Thread.currentThread().name}")
        return Retrofit.Builder()
            .baseUrl(GitHubService.END_POINT)
            .client(OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHubService::class.java)
            .listRepos(user)
    }
}
