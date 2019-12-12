package com.frank.han.di

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

/**
 *
 *
 * @author frank
 * @date 2019/12/11 7:43 PM
 */
class DITest : KoinTest {

    @Before
    fun startTheKoin() {
        startKoin {
            modules(
                serializationModule, httpClientModule, webServiceModule, databaseModule,
                repositoryModule, viewModelModule
            )
        }
    }

    @Test
    fun testHttpClientModule() {
        val retrofit1 = get<Retrofit>(named(RETROFIT_GITHUB))
        val retrofit2 = get<Retrofit>(named(RETROFIT_GITHUB))
        assertEquals(retrofit1, retrofit2)
    }

    @After
    fun stopTheKoin() {
        stopKoin()
    }
}
