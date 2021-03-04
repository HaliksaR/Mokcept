package ru.haliksar.mokcept.multimodule.core.scope

import ru.haliksar.mokcept.core.api.MokceptDsl
import ru.haliksar.mokcept.core.api.MokceptResponse
import ru.haliksar.mokcept.core.api.RESPONSE_404

interface ResponseScope {

    @MokceptDsl
    fun response(builder: MokceptResponse.() -> Unit)
}

internal class ResponseScopeImpl : ResponseScope {

    private var responseBuilder: (MokceptResponse.() -> Unit)? = null

    override fun response(builder: MokceptResponse.() -> Unit) {
        responseBuilder = builder
    }

    fun getResponse(): MokceptResponse =
        responseBuilder
            ?.let { MokceptResponse().apply(it) }
            ?: RESPONSE_404
}