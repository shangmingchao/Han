package com.frank.han.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.R
import com.google.common.truth.Truth.assertThat
import java.lang.Thread.sleep
import kotlinx.android.synthetic.main.fragment_main.counterText
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
    fun testUIState() {
        launchFragmentInContainer<MainFragment>()
        onView(withId(R.id.userBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.repoBtn)).check(matches(isDisplayed()))
    }

    @Test
    fun testUserNav() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.onFragment {
            navController.setGraph(R.navigation.nav_main)
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.userBtn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.userFragment)
    }

    @Test
    fun testRepoNav() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.onFragment {
            navController.setGraph(R.navigation.nav_main)
            Navigation.setViewNavController(it.requireView(), navController)
        }
        onView(withId(R.id.repoBtn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.repoFragment)
    }

    @Test
    fun testCounter() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<MainFragment>()
        var counter1 = ""
        scenario.onFragment {
            navController.setGraph(R.navigation.nav_main)
            Navigation.setViewNavController(it.requireView(), navController)
            counter1 = it.counterText.text.toString()
        }
        onView(withId(R.id.userBtn)).perform(click())
        sleep(2000)
        var counter2 = ""
        scenario.onFragment {
            navController.navigateUp()
            counter2 = it.counterText.text.toString()
        }
        assertThat(counter2).isNotEqualTo(counter1)
    }
}
