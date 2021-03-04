package ru.haliksar.mokcept.multimodule.core.component

import ru.haliksar.mokcept.multimodule.core.scope.RequestScope

object MokceptComponent {

    private val mockMap = mutableMapOf<String, RequestScope.() -> Unit>()

    @JvmStatic
    internal fun getMockMap(): Map<String, RequestScope.() -> Unit> = mockMap.toMap()

    @JvmStatic
    fun addMock(key: String, mock: RequestScope.() -> Unit) {
        mockMap[key] = mock
    }

    @JvmStatic
    fun removeMock(key: String) {
        mockMap.remove(key)
    }

    @JvmStatic
    fun addAll(from: Map<String, RequestScope.() -> Unit>) {
        mockMap.putAll(from)
    }

    @JvmStatic
    fun clear() {
        mockMap.clear()
    }
}

