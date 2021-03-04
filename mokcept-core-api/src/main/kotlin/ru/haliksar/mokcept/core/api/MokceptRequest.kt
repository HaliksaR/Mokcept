package ru.haliksar.mokcept.core.api

data class MokceptRequest(
    var method: Method? = null,
    var link: String? = null,
    var response: MokceptResponse? = null
)