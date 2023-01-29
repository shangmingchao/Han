package com.frank.han.util

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.frank.han.data.app.AppDatabase
import org.junit.rules.ExternalResource

/**
 * AppDatabase Rule
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
class AppDatabaseRule : ExternalResource() {

    lateinit var db: AppDatabase

    /**
     * open db
     */
    override fun before() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java,
        ).build()
    }

    /**
     * close db
     */
    override fun after() {
        db.close()
    }
}
