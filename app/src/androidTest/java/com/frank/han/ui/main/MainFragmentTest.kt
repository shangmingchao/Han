package com.frank.han.ui.main

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
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
import com.frank.han.widget.HTextView
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

/**
 * MainFragment UI test
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    /**
     * testUIState
     */
    @Test
    fun testUIState() {
        launchFragmentInContainer<MainFragment>()
        onView(withId(R.id.userBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.repoBtn)).check(matches(isDisplayed()))
        onView(withId(R.id.articleBtn)).check(matches(isDisplayed()))
    }

    /**
     * testUserNav
     */
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

    /**
     * testRepoNav
     */
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
        scenario.onFragment { navController.navigateUp() }
        onView(withId(R.id.userBtn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.userFragment)
        sleep(1000)
        scenario.onFragment { navController.navigateUp() }
        onView(withId(R.id.repoBtn)).perform(click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.repoFragment)
    }

    /**
     * testArticleNav
     */
    @Test
    fun testArticleNav() {
        val scenario = launchFragmentInContainer<MainFragment>()
        scenario.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.articleBtn)).perform(click())
    }

    /**
     * testCounter
     */
    @Test
    fun testCounter() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val scenario = launchFragmentInContainer<MainFragment>()
        var counter1 = ""
        scenario.onFragment {
            navController.setGraph(R.navigation.nav_main)
            Navigation.setViewNavController(it.requireView(), navController)
            counter1 = it.requireView().findViewById<HTextView>(R.id.counterText).text.toString()
        }
        onView(withId(R.id.userBtn)).perform(click())
        sleep(2000)
        var counter2 = ""
        scenario.onFragment {
            navController.navigateUp()
            counter2 = it.requireView().findViewById<HTextView>(R.id.counterText).text.toString()
        }
        assertThat(counter2).isNotEqualTo(counter1)
    }
}
