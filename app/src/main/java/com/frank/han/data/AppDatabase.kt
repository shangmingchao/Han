package com.frank.han.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frank.han.data.repo.RepoDao
import com.frank.han.data.repo.entity.Repo
import com.frank.han.data.user.UserDao
import com.frank.han.data.user.entity.User

/**
 *
 *
 * @author frank
 * @date 2019/12/7 8:16 PM
 */
@Database(entities = [Repo::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun repoDao(): RepoDao

    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "han.db"
    }
}
