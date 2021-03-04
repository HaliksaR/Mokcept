package ru.haliksar.mokcept.core

import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import ru.haliksar.mokcept.core.api.CONTENT_TYPE
import ru.haliksar.mokcept.core.api.MEDIA_TYPE_JSON
import ru.haliksar.mokcept.core.api.Method
import ru.haliksar.mokcept.core.api.enumValueOrNull
import ru.haliksar.mokcept.core.handler.MethodHandler
import ru.haliksar.mokcept.core.request.MokceptRequest
import ru.haliksar.mokcept.core.dsl.response404
import java.net.URI


class Mokcept(
    private val protocol: Protocol = Protocol.HTTP_2,
    private val handlers: List<MethodHandler> = emptyList()
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response =
        with(chain.request()) {
            val response = Response.Builder()
                .request(this)
                .protocol(protocol)
                .addHeader(CONTENT_TYPE, MEDIA_TYPE_JSON)

            response.handleResponse(url.toUri(), method).build()
        }


    private fun Response.Builder.handleResponse(
        url: URI,
        method: String,
    ): Response.Builder {
        handlers.forEach { handler ->
            if (handler.method == enumValueOrNull<Method>(method)) {
                return findRequest(url, handler.requests)
            }
        }
        return response404()
    }

    private fun Response.Builder.findRequest(
        uri: URI,
        requests: List<MokceptRequest>,
    ): Response.Builder {
        requests.forEach {
            if (it.link == uri.path) {
                it.response(this, uri)
                return this
            }
        }
        return response404()
    }

}