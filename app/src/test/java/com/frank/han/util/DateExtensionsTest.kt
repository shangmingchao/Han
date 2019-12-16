package com.frank.han.util

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
    fun isSameYear() {
        assert(Calendar.getInstance().isSameYear(Calendar.getInstance()))
    }
}
