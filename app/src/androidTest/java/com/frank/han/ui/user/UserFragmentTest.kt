package com.frank.han.ui.user

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.R
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UserFragment UI test
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class UserFragmentTest {

    @Test
    fun testEvent() {
        val scenario = launchFragmentInContainer<UserFragment>(bundleOf("username" to "google"))
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(ViewMatchers.withId(R.id.userTextView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
