package com.frank.han.di

import com.frank.han.api.github.RepoService
import com.frank.han.api.github.UserService
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val webServiceModule = module {
    single<RepoService> { get<Retrofit>(named(RETROFIT_GITHUB)).create(
        RepoService::class.java) }
    single<UserService> { get<Retrofit>(named(RETROFIT_GITHUB)).create(
        UserService::class.java) }
}
