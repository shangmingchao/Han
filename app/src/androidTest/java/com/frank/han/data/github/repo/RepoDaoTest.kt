package com.frank.han.data.github.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.data.github.repo.entity.RepoPO
import com.frank.han.util.AppDatabaseRule
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

    @Test
    @Throws(Exception::class)
    fun testUser() = runBlocking {
        val repoDao = dbRule.db.repoDao()
        val repos = listOf(RepoPO(1L, "name1", false, 1L))
        repoDao.saveRepo(repos)
        val expected = repoDao.getUserRepos("login1").first()
        assertThat(expected).isNull()
    }
}
