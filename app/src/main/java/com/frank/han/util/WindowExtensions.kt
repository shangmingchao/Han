package com.frank.han.util

import android.view.Window
import androidx.core.view.ViewCompat.getWindowInsetsController
import androidx.core.view.WindowInsetsCompat.Type.systemBars

/**
 * Window Extensions
 *
 * @author frank
 * @date 2019/12/16 3:51 PM
 */
fun Window.hideSystemUI() {
    getWindowInsetsController(decorView)?.hide(systemBars())
}
