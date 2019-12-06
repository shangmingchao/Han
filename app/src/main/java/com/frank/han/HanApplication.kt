package com.frank.han

import android.app.Application
import com.facebook.stetho.Stetho

/**
 *
 *
 * @author frank
 * @date 2019/12/3 12:06 PM
 */
class HanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}
