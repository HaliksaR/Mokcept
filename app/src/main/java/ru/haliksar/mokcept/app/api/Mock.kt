package ru.haliksar.mokcept.app.api

import android.content.Context
import ru.haliksar.mokcept.android.ext.json
import ru.haliksar.mokcept.app.R
import ru.haliksar.mokcept.core.api.Method
import ru.haliksar.mokcept.multimodule.core.component.MokceptComponent


internal val MokceptComponent.simplePost: (Context) -> Unit
    get() = { context ->
        addMock("simplePost") {
            request(
                method = Method.POST,
                link = "/api/v1/simplePost"
            ) { uri ->
                response {
                    code = 200
                    message = "Give SimpleSet"
                }
            }
        }
    }

internal val MokceptComponent.simpleQuery: (Context) -> Unit
    get() = { context ->
        addMock("simpleQuery") {
            requestWithQuery(
                method = Method.GET,
                link = "/api/v1/simpleQuery",
                key = { it.query }
            ) { uri ->
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

internal val MokceptComponent.simple: (Context) -> Unit
    get() = { context ->
        addMock("simple") {
            request(
                method = Method.GET,
                link = "/api/v1/simple"
            ) { uri ->
                response {
                    code = 200
                    message = "Take SimpleSet"
                    body = context json R.raw.simple_data
                }
            }
        }
    }