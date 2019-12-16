package com.frank.han.util

import java.util.Calendar

/**
 * Date Extensions
 *
 * @author frank
 * @date 2019/12/2 10:41 AM
 */
fun Calendar.isSameYear(calendar: Calendar): Boolean {
    return get(Calendar.YEAR) == calendar.get(Calendar.YEAR)
}
