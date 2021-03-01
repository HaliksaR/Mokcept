package ru.haliksar.mokcept.app.di

import org.koin.dsl.module
import ru.haliksar.mokcept.app.api.TestApi


val MainModule = module {
    factory<TestApi> { createRetrofitService(get()) }
}