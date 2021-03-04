package ru.haliksar.mokcept.app.api

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.haliksar.mokcept.multimodule.core.component.MokceptComponent

interface DataSource {
    suspend fun simple(): SimpleSet

    suspend fun simpleQuery(number: Int): List<SimpleSet>

    suspend fun simplePost(data: SimpleSet)
}

class DataSourceImpl(
    private val api: TestApi
) : DataSource {

    override suspend fun simple(): SimpleSet =
        withContext(Dispatchers.IO) {
            api.simple()
        }

    override suspend fun simpleQuery(number: Int): List<SimpleSet> =
        withContext(Dispatchers.IO) {
            api.simpleQuery(number)
        }

    override suspend fun simplePost(data: SimpleSet) =
        withContext(Dispatchers.IO) {
            api.simplePost(data)
        }

}

class DataSourceMultiModuleImpl(
    private val api: TestApi,
    context: Context
) : DataSource {

    init {
        MokceptComponent.simple(context)
        MokceptComponent.simpleQuery(context)
        MokceptComponent.simplePost(context)
    }

    override suspend fun simple(): SimpleSet =
        withContext(Dispatchers.IO) {
            api.simple()
        }

    override suspend fun simpleQuery(number: Int): List<SimpleSet> =
        withContext(Dispatchers.IO) {
            api.simpleQuery(number)
        }

    override suspend fun simplePost(data: SimpleSet) =
        withContext(Dispatchers.IO) {
            api.simplePost(data)
        }

}