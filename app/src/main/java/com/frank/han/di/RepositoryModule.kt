package com.frank.han.di

import androidx.datastore.preferences.createDataStore
import com.frank.han.data.app.AppPrefs
import com.frank.han.data.app.STORE_SETTINGS
import com.frank.han.data.github.repo.RepoRepository
import com.frank.han.data.github.user.UserRepository
import com.frank.han.data.wan.wechat.ArticleRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { androidContext().createDataStore(STORE_SETTINGS) }
    single { AppPrefs(get()) }
    single { RepoRepository(get(), get()) }
    single { UserRepository(get(), get()) }
    single { ArticleRepository(get()) }
}
