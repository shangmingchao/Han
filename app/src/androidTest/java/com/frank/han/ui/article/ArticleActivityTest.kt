package com.frank.han.ui.article

import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * ArticleActivity UI test
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ArticleActivityTest {

    @get:Rule
    var articleActivityRule = activityScenarioRule<ArticleActivity>()

    /**
     * Test ArticleActivity's event
     */
    @Test
    fun testEvent() {
        articleActivityRule.scenario.recreate()
    }
}
