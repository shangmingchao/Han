package com.frank.han.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.frank.han.data.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DATABASE_APP = "app"

val databaseModule = module {

    factory(named(DATABASE_APP)) {
        Room.databaseBuilder(
            androidContext(), AppDatabase::class.java, AppDatabase.DATABASE_NAME
        )
    }

    single { get<RoomDatabase.Builder<AppDatabase>>(named(DATABASE_APP)).build() }

    single { get<AppDatabase>().repoDao() }

    single { get<AppDatabase>().userDao() }
}
