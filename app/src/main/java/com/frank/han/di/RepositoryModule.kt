package com.frank.han.di

import com.frank.han.data.repo.RepoRepository
import com.frank.han.data.user.UserRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RepoRepository(get(), get()) }
    single { UserRepository(get(), get()) }
}
