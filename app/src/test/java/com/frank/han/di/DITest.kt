package com.frank.han.di

import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.get
import retrofit2.Retrofit

/**
 *
 *
 * @author frank
 * @date 2019/12/11 7:43 PM
 */
class DITest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(
            serializationModule, httpClientModule, webServiceModule, databaseModule,
            repositoryModule, viewModelModule
        )
    }

    @Test
    fun testHttpClientModule() {
        val retrofit1 = get<Retrofit>(named(RETROFIT_GITHUB))
        val retrofit2 = get<Retrofit>(named(RETROFIT_GITHUB))
        assertThat(retrofit1).isEqualTo(retrofit2)
    }

}
