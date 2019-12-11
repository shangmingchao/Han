package com.frank.han.di

import com.frank.han.api.RepoService
import org.koin.dsl.module
import retrofit2.Retrofit

val webServiceModule = module {
    single<RepoService> { get<Retrofit>().create(RepoService::class.java) }
}
