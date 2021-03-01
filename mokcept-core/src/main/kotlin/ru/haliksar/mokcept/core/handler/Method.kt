@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")

package ru.haliksar.mokcept.core.handler

enum class Method {
    GET,
    PUT,
    POST,
    DELETE,
    PATCH
}

inline fun <reified T : Enum<*>> enumValueOrNull(name: String): T? =
    T::class.java.enumConstants.firstOrNull { it.name == name }