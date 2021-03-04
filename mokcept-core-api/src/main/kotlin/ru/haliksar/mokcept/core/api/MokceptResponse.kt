package ru.haliksar.mokcept.core.api

import okhttp3.Protocol

private const val DEFAULT_CODE = 200
private const val DEFAULT_MESSAGE = "description not init"
private const val DEFAULT_BODY = ""

data class MokceptResponse(
    var code: Int = DEFAULT_CODE,
    var message: String = DEFAULT_MESSAGE,
    var body: String = DEFAULT_BODY,
    var protocol: Protocol = Protocol.HTTP_2
)

val RESPONSE_404 = MokceptResponse(
    code = 404,
    message = "NOT_FOUND",
    body = """{ "error": "NOT API" }"""
)