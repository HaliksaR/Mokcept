package ru.haliksar.mokcept.core.request

import okhttp3.Response
import java.net.URI

data class MokceptRequest(
    val link: String,
    val response: Response.Builder.(URI) -> Unit
)
