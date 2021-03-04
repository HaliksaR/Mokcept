package ru.haliksar.mokcept.multimodule.core.scope

import ru.haliksar.mokcept.core.api.Method
import ru.haliksar.mokcept.core.api.MokceptDsl
import ru.haliksar.mokcept.core.api.MokceptRequest
import java.net.URI

interface RequestScope {

    @MokceptDsl
    fun request(
        method: Method,
        link: String,
        builder: ResponseScope.(URI) -> Unit
    )

    @MokceptDsl
    fun <T : Any?> requestWithQuery(
        method: Method,
        link: String,
        key: (URI) -> T,
        responses: ResponseWithQueryScope<T>.(URI) -> Unit
    )
}

internal class RequestScopeImpl(
    private val uri: URI
) : RequestScope {

    private val requests = mutableListOf<MokceptRequest>()

    fun getRequests(): List<MokceptRequest> = requests.toList()

    override fun request(
        method: Method,
        link: String,
        builder: ResponseScope.(URI) -> Unit
    ) {
        val requestScope = ResponseScopeImpl()
        builder(requestScope, uri)
        requests.add(
            MokceptRequest(
                method = method,
                link = link,
                response = requestScope.getResponse()
            )
        )
    }

    override fun <T : Any?> requestWithQuery(
        method: Method,
        link: String,
        key: (URI) -> T,
        responses: ResponseWithQueryScope<T>.(URI) -> Unit
    ) {
        val requestScope = ResponseWithQueryScopeImpl(searchKey = key(uri))
        responses(requestScope as ResponseWithQueryScope<T>, uri)
        requests.add(
            MokceptRequest(
                method = method,
                link = link,
                response = requestScope.findResponse()
            )
        )
    }
}