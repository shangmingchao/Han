package com.frank.han.util

import com.google.common.truth.Truth.assertThat
import java.util.Calendar
import org.junit.Test

/**
 * DateExtensions test
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
class DateExtensionsTest {

    @Test
    fun testSameYear() {
        assertThat(Calendar.getInstance().isSameYear(Calendar.getInstance())).isTrue()
    }
}
