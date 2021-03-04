package ru.haliksar.mokcept.app.di

import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.haliksar.mokcept.app.api.DataSource
import ru.haliksar.mokcept.app.api.DataSourceImpl
import ru.haliksar.mokcept.app.api.DataSourceMultiModuleImpl
import ru.haliksar.mokcept.app.api.TestApi


internal val BASE = "BASE"
internal val MULTI_MODULE = "MULTI_MODULE"

val MainModule = module {
    factory<TestApi>(named(BASE)) { createRetrofitService(get(named(BASE))) }
    single<DataSource>(named(BASE)) { DataSourceImpl(get(named(BASE))) }

    factory<TestApi>(named(MULTI_MODULE)) {
        createRetrofitService(get(named(MULTI_MODULE)))
    }
    single<DataSource>(named(MULTI_MODULE)) {
        DataSourceMultiModuleImpl(
            get(named(MULTI_MODULE)),
            androidContext()
        )
    }
}