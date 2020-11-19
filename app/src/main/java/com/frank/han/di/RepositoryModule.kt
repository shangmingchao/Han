package com.frank.han.di

import androidx.datastore.preferences.createDataStore
import com.frank.han.data.app.AppPrefs
import com.frank.han.data.app.STORE_SETTINGS
import com.frank.han.data.repo.RepoRepository
import com.frank.han.data.user.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { androidContext().createDataStore(STORE_SETTINGS) }
    single { AppPrefs(get()) }
    single { RepoRepository(get(), get()) }
    single { UserRepository(get(), get()) }
}
