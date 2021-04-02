package com.frank.han

import android.app.Application

/**
 *
 *
 * @author frank
 * @date 2019/12/3 12:06 PM
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
    }

    companion object {
        private var app: Application? = null
        val instance get() = app!!
    }
}
