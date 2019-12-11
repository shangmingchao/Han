package com.frank.han.di

import com.google.gson.Gson
import org.koin.dsl.module

val serializationModule = module {
    single { Gson() }
}
