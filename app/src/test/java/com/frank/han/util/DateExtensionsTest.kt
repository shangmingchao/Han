package com.frank.han.util

import java.util.Calendar
import org.junit.Test

class DateExtensionsTest {

    @Test
    fun isSameYear() {
        assert(DateExtensions.isSameYear(Calendar.getInstance(), Calendar.getInstance()))
    }
}
