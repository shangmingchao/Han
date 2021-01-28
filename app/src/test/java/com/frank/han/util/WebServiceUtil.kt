package com.frank.han.util

import com.frank.han.api.GITHUB_END_POINT
import com.frank.han.api.WAN_END_POINT
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Get GitHub retrofit
 * @return Retrofit
 */
fun getGitHubRetrofit() = Retrofit.Builder()
    .baseUrl(GITHUB_END_POINT)
    .client(OkHttpClient())
    .addConverterFactory(GsonConverterFactory.create(Gson()))
    .build()

/**
 * Get Wan retrofit
 * @return Retrofit
 */
fun getWanRetrofit() = Retrofit.Builder()
    .baseUrl(WAN_END_POINT)
    .client(OkHttpClient())
    .addConverterFactory(GsonConverterFactory.create(Gson()))
    .build()
