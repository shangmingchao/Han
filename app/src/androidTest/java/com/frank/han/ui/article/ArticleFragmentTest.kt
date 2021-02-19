package com.frank.han.ui.article

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.frank.han.R
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep

/**
 * ArticleFragment UI test
 *
 * @author frank
 * @date 2021/1/11 11:46 AM
 */
@RunWith(AndroidJUnit4::class)
class ArticleFragmentTest {

    /**
     * Test ArticleFragment's event
     */
    @Test
    fun testEvent() {
        launchFragmentInContainer<ArticleFragment>()
        onView(withId(R.id.articleTextView))
            .check(matches(withContentDescription(R.string.article)))
        sleep(5000)
    }
}
