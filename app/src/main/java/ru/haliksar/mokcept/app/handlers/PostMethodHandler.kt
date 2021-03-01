package ru.haliksar.mokcept.app.handlers

import android.content.Context
import ru.haliksar.mokcept.core.handler.Method
import ru.haliksar.mokcept.core.handler.MethodHandler
import ru.haliksar.mokcept.core.dsl.request
import ru.haliksar.mokcept.core.dsl.response

class PostMethodHandler(private val context: Context) : MethodHandler(Method.POST) {
    init {
        request("/api/v1/simplePost") { uri ->
            response {
                code = 200
                message = "Give SimpleSet"
            }
        }
    }
}