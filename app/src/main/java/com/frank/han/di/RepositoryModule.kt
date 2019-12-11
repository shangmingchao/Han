package com.frank.han.di

import com.frank.han.data.repo.RepoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RepoRepository(get(), get()) }
}
