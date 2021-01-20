package com.frank.han.ui.repo

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
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
class RepoFragmentTest {

    @Test
    fun testEvent() {
        launchFragmentInContainer<RepoFragment>(bundleOf("username" to "google"))
        onView(withId(R.id.repoText)).check(matches(withContentDescription(R.string.repo)))
        Thread.sleep(2000)
    }
}
