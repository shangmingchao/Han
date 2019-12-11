package com.frank.han.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.frank.han.BuildConfig
import com.frank.han.api.RepoService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BASIC
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val INTERCEPTOR_LOGCAT = "logcat"
const val INTERCEPTOR_CONSOLE = "console"

val httpClientModule = module {

    factory { OkHttpClient.Builder() }

    factory { Retrofit.Builder() }

    single<Interceptor>(named(INTERCEPTOR_LOGCAT)) {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BASIC else NONE
        }
    }

    single<Interceptor>(named(INTERCEPTOR_CONSOLE)) {
        StethoInterceptor()
    }

    single {
        get<OkHttpClient.Builder>()
            .addNetworkInterceptor(get<Interceptor>(named(INTERCEPTOR_LOGCAT)))
            .addNetworkInterceptor(get<Interceptor>(named(INTERCEPTOR_CONSOLE)))
            .build()
    }

    single {
        get<Retrofit.Builder>()
            .baseUrl(RepoService.END_POINT)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
}
