package ru.haliksar.mokcept.multimodule.core.scope

import ru.haliksar.mokcept.core.api.MokceptDsl
import ru.haliksar.mokcept.core.api.MokceptResponse
import ru.haliksar.mokcept.core.api.RESPONSE_404


interface ResponseWithQueryScope<T : Any?> {

    @MokceptDsl
    fun response(key: T?, builder: MokceptResponse.() -> Unit)

    @MokceptDsl
    fun default(builder: MokceptResponse.() -> Unit)
}


internal class ResponseWithQueryScopeImpl<T : Any?>(
    private val searchKey: T
) : ResponseWithQueryScope<T?> {

    internal object DEFAULT_KEY

    private val responseMap = mutableMapOf<Any?, MokceptResponse.() -> Unit>()

    override fun response(
        key: T?,
        builder: MokceptResponse.() -> Unit
    ) {
        responseMap[key] = builder
    }

    override fun default(builder: MokceptResponse.() -> Unit) {
        responseMap[DEFAULT_KEY] = builder
    }

    fun findResponse(): MokceptResponse {
        responseMap[searchKey]?.let { return MokceptResponse().apply(it) }
        responseMap[DEFAULT_KEY]?.let { return MokceptResponse().apply(it) }
        return RESPONSE_404
    }
}

