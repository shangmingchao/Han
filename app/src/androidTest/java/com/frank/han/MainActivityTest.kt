package com.frank.han

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MainActivityTest java -jar crawl_launcher.jar --apk-file /Users/hhh/AndroidStudioProjects/Han/app/release/release_1.0.0.0.apk --android-sdk /Users/hhh/Library/Android/sdk
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testNameChange() {
        onView(withId(R.id.name)).check(matches(withText("test = true")))
    }
}
