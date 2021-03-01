package ru.haliksar.mokcept.app.handlers

import android.content.Context
import ru.haliksar.mokcept.android.ext.json
import ru.haliksar.mokcept.app.R
import ru.haliksar.mokcept.core.dsl.*
import ru.haliksar.mokcept.core.handler.Method
import ru.haliksar.mokcept.core.handler.MethodHandler

class GetMethodHandler(private val context: Context) : MethodHandler(Method.GET) {
    init {
        request("/api/v1/simple") { uri ->
            response {
                code = 200
                message = "Take SimpleSet"
                body = context json R.raw.simple_data
            }
        }
        request("/api/v1/simpleQuery") { uri ->
            withQuery(key = uri.query) {
                response("number=1") {
                    code = 200
                    message = "first page"
                    body = context json R.raw.simple_data_first
                }
                response("number=2") {
                    code = 200
                    message = "second page"
                    body = context json R.raw.simple_data_second
                }
                default {
                    code = 200
                    message = "last page"
                    body = context json R.raw.simple_data_last
                }
            }
        }
    }
}