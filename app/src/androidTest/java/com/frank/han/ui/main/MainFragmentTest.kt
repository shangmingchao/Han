package com.frank.han.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.R
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MainFragment UI test
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @Test
    fun testEvent() {
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.userBtn)).check(matches(isDisplayed()))
    }
}
