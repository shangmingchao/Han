package com.frank.han

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MainActivity UI test
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var mainActivityRule = activityScenarioRule<MainActivity>()

    @Test
    fun testEvent() {
        val scenario = mainActivityRule.scenario
        scenario.recreate()
    }
}
