package com.frank.han

import android.app.Application
import com.facebook.stetho.Stetho
import com.frank.han.di.databaseModule
import com.frank.han.di.httpClientModule
import com.frank.han.di.repositoryModule
import com.frank.han.di.serializationModule
import com.frank.han.di.viewModelModule
import com.frank.han.di.webServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/**
 *
 *
 * @author frank
 * @date 2019/12/3 12:06 PM
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                serializationModule, httpClientModule, webServiceModule, databaseModule,
                repositoryModule, viewModelModule
            )
        }
    }
}
