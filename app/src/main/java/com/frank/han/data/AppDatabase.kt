package com.frank.han.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.frank.han.data.repo.RepoDao
import com.frank.han.data.repo.entity.Repo
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

    companion object {

        private const val DATABASE_NAME = "han.db"

        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
        }
    }
}
