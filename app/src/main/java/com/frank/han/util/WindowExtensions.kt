package com.frank.han.util

import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import android.view.Window
import android.view.WindowManager.LayoutParams

/**
 * Window Extensions
 *
 * @author frank
 * @date 2019/12/16 3:51 PM
 */
fun Window.lightStatusBar() {
    if (VERSION.SDK_INT >= VERSION_CODES.M) {
        addFlags(LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        clearFlags(LayoutParams.FLAG_TRANSLUCENT_STATUS)
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
