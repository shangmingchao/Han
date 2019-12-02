package com.frank.han.util

import java.util.Calendar

/**
 * DateExtensions
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
object DateExtensions {

    fun isSameYear(cal1: Calendar, cal2: Calendar): Boolean {
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
    }
}
