package com.frank.han

import android.app.Application
import com.facebook.stetho.Stetho
import kotlin.properties.Delegates

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
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    companion object {
        private var app: App by Delegates.notNull()
        fun getInstance() = app
    }
}
