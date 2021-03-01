package ru.haliksar.mokcept.core.dsl

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import ru.haliksar.mokcept.core.MEDIA_TYPE_JSON
import ru.haliksar.mokcept.core.handler.MethodHandler
import ru.haliksar.mokcept.core.request.MokceptRequest
import ru.haliksar.mokcept.core.response.MokceptResponse
import java.net.URI

@DslMarker
annotation class MokceptDsl

typealias MokceptResponseBuilder = MokceptResponse.() -> Unit
typealias MokceptResponsesBuilder = MokceptResponses.() -> Unit
typealias MokceptResponses = MutableMap<Key, MokceptResponseBuilder?>
typealias Key = Any?
private object DEFAULT_KEY

@MokceptDsl
fun MethodHandler.request(
    link: String,
    builder: Response.Builder.(URI) -> Unit
) = requests.add(MokceptRequest(link, builder))

@MokceptDsl
fun MokceptResponses.response(
    key: Key,
    builder: MokceptResponseBuilder
) = put(key, builder)

@MokceptDsl
fun MokceptResponses.default(
    builder: MokceptResponseBuilder
) = put(DEFAULT_KEY, builder)

@MokceptDsl
fun Response.Builder.withQuery(
    key: Key,
    responses: MokceptResponsesBuilder
): Response.Builder {
    val map = mutableMapOf<Any?, MokceptResponseBuilder?>().apply(responses)
    map[key]?.let { return response(it) }
    map[DEFAULT_KEY]?.let { return response(it) }
    return response404()
}

@MokceptDsl
fun Response.Builder.response(
    builder: MokceptResponseBuilder
) = with(MokceptResponse().apply(builder)) {
    code(code)
        .message(message)
        .protocol(protocol)
        .body(body.toResponseBody(MEDIA_TYPE_JSON.toMediaTypeOrNull()))
}

@MokceptDsl
fun Response.Builder.response404() =
    response {
        code = 404
        message = "NOT_FOUND"
        body = """{ "error": "NOT API" }"""
    }
