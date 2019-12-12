package com.frank.han.ui.repo

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author frank
 * @date 2019/12/12 9:34 AM
 */
@RunWith(AndroidJUnit4::class)
class RepoActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RepoActivity::class.java)

    @Test
    fun testRecreate() {
        val scenario = activityScenarioRule.scenario
        scenario.recreate()
        scenario.onActivity {
            val repoFragment =
                it.supportFragmentManager.findFragmentById(R.id.container) as RepoFragment
            assertNotNull(repoFragment)
            assertEquals("brief", repoFragment.getStyle())
        }
    }
}
