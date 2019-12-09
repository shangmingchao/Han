package com.frank.han

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.ComponentNameMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.matcher.IntentMatchers.toPackage
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MainActivity UI test
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */

private const val PACKAGE = "com.frank.han"

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityBehaviorUiTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Test
    fun testRepoRouter() {
        onView(withId(R.id.repo)).perform(click())
        intended(
            allOf(
                IntentMatchers.hasComponent(
                    ComponentNameMatchers.hasShortClassName(".ui.repo.RepoActivity")
                ),
                toPackage(PACKAGE)
            )
        )
    }
}
