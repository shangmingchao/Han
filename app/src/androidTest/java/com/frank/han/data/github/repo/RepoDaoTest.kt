package com.frank.han.data.github.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.util.AppDatabaseRule
import com.frank.han.util.MOCKED_USER_LOGIN
import com.frank.han.util.mockedRepoPO
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Rule
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

    @get:Rule
    val dbRule = AppDatabaseRule()

    /**
     * Test RepoDao
     *
     * @throws Exception
     */
    @Test
    @Throws(Exception::class)
    fun testRepo() = runBlocking {
        val repoDao = dbRule.db.repoDao()
        val repos = listOf(mockedRepoPO)
        repoDao.saveRepo(repos)
        val expected = repoDao.getUserRepos(MOCKED_USER_LOGIN).first()
        assertThat(expected).isNull()
    }
}
