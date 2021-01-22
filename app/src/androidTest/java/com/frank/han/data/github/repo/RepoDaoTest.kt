package com.frank.han.data.github.repo

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.data.app.AppDatabase
import com.frank.han.data.github.repo.entity.RepoPO
import com.google.common.truth.Truth.assertThat
import java.io.IOException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * RepoDaoTest
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class RepoDaoTest {

    private lateinit var repoDao: RepoDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        repoDao = db.repoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testUser() = runBlocking {
        val repos = listOf(RepoPO(1L, "name1", false, 1L))
        repoDao.saveRepo(repos)
        val expected = repoDao.getUserRepos("login1").first()
        assertThat(expected).isNull()
    }
}
