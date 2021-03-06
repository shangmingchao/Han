package com.frank.han.data.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.frank.han.data.github.repo.RepoDao
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.data.github.user.UserDao
import com.frank.han.data.github.user.entity.UserPO

/**
 * AppDatabase
 *
 * @see <a href="https://developer.android.com/training/data-storage/room">Room</a>
 * @author frank
 * @date 2019/12/7 8:16 PM
 */
@Database(entities = [RepoPO::class, UserPO::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    /**
     * Repo DAO
     *
     * @return RepoDao
     */
    abstract fun repoDao(): RepoDao

    /**
     * user DAO
     *
     * @return UserDao
     */
    abstract fun userDao(): UserDao

    companion object {
        const val DATABASE_NAME = "han.db"
    }
}
