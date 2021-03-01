package ru.haliksar.mokcept.app.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


@JsonClass(generateAdapter = true)
data class SimpleSet(
    @Json(name = "title") val title: String,
    @Json(name = "description") val description: String
)

interface TestApi {

    @GET("/api/v1/simple")
    suspend fun simple(): SimpleSet

    @GET("/api/v1/simpleQuery")
    suspend fun simpleQuery(@Query("number") number: Int): List<SimpleSet>

    @POST("/api/v1/simplePost")
    suspend fun simplePost(@Body data: SimpleSet)
}