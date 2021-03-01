package ru.haliksar.mokcept.core.handler

import ru.haliksar.mokcept.core.request.MokceptRequest


abstract class MethodHandler(internal val method: Method) {

    val requests: MutableList<MokceptRequest> = mutableListOf()
}