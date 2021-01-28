package com.frank.han.data.github.user

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.data.github.user.entity.UserPO
import com.frank.han.util.AppDatabaseRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UserDaoTest
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class UserDaoTest {

    @get:Rule
    val dbRule = AppDatabaseRule()

    @Test
    @Throws(Exception::class)
    fun testUser() = runBlocking {
        val userDao = dbRule.db.userDao()
        val user = UserPO(1L, "login1", "name1")
        userDao.saveUser(user)
        val idUser = userDao.getUserById("1").first()
        assertThat(idUser).isEqualTo(user)
        val nameUser = userDao.getUserByName("login1").first()
        assertThat(nameUser).isEqualTo(user)
    }
}
