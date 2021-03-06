package com.frank.han.data.github.user

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.util.AppDatabaseRule
import com.frank.han.util.MOCKED_USER_LOGIN
import com.frank.han.util.MOCKED_USER_NAME
import com.frank.han.util.mockedUserPO
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

    /**
     * Test UserDao
     *
     * @throws Exception
     */
    @Test
    @Throws(Exception::class)
    fun testUser() = runBlocking {
        val userDao = dbRule.db.userDao()
        val user = mockedUserPO
        userDao.saveUser(user)
        val idUser = userDao.getUserById(MOCKED_USER_LOGIN).first()
        assertThat(idUser).isEqualTo(user)
        val nameUser = userDao.getUserByName(MOCKED_USER_NAME).first()
        assertThat(nameUser).isEqualTo(user)
    }
}
