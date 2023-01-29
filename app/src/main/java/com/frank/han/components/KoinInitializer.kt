package com.frank.han.components

import android.content.Context
import androidx.startup.Initializer
import com.frank.han.di.databaseModule
import com.frank.han.di.httpClientModule
import com.frank.han.di.repositoryModule
import com.frank.han.di.serializationModule
import com.frank.han.di.viewModelModule
import com.frank.han.di.webServiceModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

/**
 * Koin Initializer
 *
 * @author frank
 * @date 2019/12/17 3:52 PM
 */
class KoinInitializer : Initializer<KoinApplication> {

    override fun create(context: Context): KoinApplication {
        return startKoin {
            androidLogger()
            androidContext(context)
            modules(
                serializationModule,
                httpClientModule,
                webServiceModule,
                databaseModule,
                repositoryModule,
                viewModelModule,
            )
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}
