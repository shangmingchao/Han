package com.frank.han.data.github.user

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.data.app.AppDatabase
import com.frank.han.data.github.user.entity.UserPO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * UserDaoTest
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    private lateinit var userDao: UserDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        userDao = db.userDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testUser() = runBlocking {
        val user = UserPO(1L, "login1", "name1")
        userDao.saveUser(user)
        val idUser = userDao.getUserById("1").first()
        assertThat(idUser).isEqualTo(user)
        val nameUser = userDao.getUserByName("login1").first()
        assertThat(nameUser).isEqualTo(user)
    }
}
