package ru.haliksar.mokcept.multimodule.core

import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.haliksar.mokcept.core.api.*
import ru.haliksar.mokcept.multimodule.core.component.MokceptComponent
import ru.haliksar.mokcept.multimodule.core.scope.RequestScope
import ru.haliksar.mokcept.multimodule.core.scope.RequestScopeImpl
import java.net.URI

class Mokcept(
    private val protocol: Protocol = Protocol.HTTP_2
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val responseMokcept = handleResponse(request.url.toUri(), request.method)
        return Response.Builder()
            .request(request)
            .protocol(protocol)
            .addHeader(CONTENT_TYPE, MEDIA_TYPE_JSON)
            .code(responseMokcept.code)
            .message(responseMokcept.message)
            .body(responseMokcept.body.toResponseBody(MEDIA_TYPE_JSON.toMediaTypeOrNull()))
            .build()
    }

    private fun handleResponse(
        uri: URI,
        method: String,
    ): MokceptResponse {
        val requestScopeBuilderList = mutableListOf<RequestScope.() -> Unit>()
        MokceptComponent.getMockMap().values.forEach { requestScopeBuilderList.add(it) }
        return findResponse(requestScopeBuilderList, method, uri)
    }

    private fun findResponse(
        requestScopeBuilderList: MutableList<RequestScope.() -> Unit>,
        method: String,
        uri: URI
    ): MokceptResponse {
        requestScopeBuilderList.forEach { requestScopeBuilder ->
            val requestScope = RequestScopeImpl(uri)
            requestScopeBuilder(requestScope)
            val requests = requestScope.getRequests()
            requests.forEach { request ->
                if (request.method == enumValueOrNull<Method>(method) && request.link == uri.path) {
                    return request.response ?: RESPONSE_404
                }
            }
        }
        return RESPONSE_404
    }
}


