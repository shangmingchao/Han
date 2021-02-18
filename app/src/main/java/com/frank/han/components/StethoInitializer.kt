package com.frank.han.components

import android.content.Context
import androidx.startup.Initializer
import com.facebook.stetho.Stetho
import com.frank.han.BuildConfig

/**
 * Stetho Initializer
 *
 * @author frank
 * @date 2019/12/17 3:52 PM
 */
class StethoInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(context)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
