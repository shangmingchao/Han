package com.frank.han.di

import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.context.GlobalContext.stopKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.test.KoinTest
import org.koin.test.get
import retrofit2.Retrofit

/**
 * DITest
 *
 * @author frank
 * @date 2019/12/11 7:43 PM
 */
class DITest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule(
        modules = listOf(
            serializationModule,
            httpClientModule,
            webServiceModule,
            databaseModule,
            repositoryModule,
            viewModelModule,
        ),
    )

    /**
     * testHttpClientModule
     */
    @Test
    fun testHttpClientModule() {
        val retrofit1 = get<Retrofit>(named(RETROFIT_GITHUB))
        val retrofit2 = get<Retrofit>(named(RETROFIT_GITHUB))
        assertThat(retrofit1).isEqualTo(retrofit2)
    }
}

class KoinTestRule(
    private val modules: List<Module>,
) : TestWatcher() {
    override fun starting(description: Description) {
        startKoin {
            modules(modules)
        }
    }

    override fun finished(description: Description) {
        stopKoin()
    }
}
