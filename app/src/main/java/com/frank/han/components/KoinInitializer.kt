package com.frank.han.components

import android.content.Context
import androidx.startup.Initializer
import com.frank.han.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidLogger()
            androidContext(context)
            modules(
                    serializationModule, httpClientModule, webServiceModule, databaseModule,
                    repositoryModule, viewModelModule
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
