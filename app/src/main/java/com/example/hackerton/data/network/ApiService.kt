package com.example.hackerton.data.network

import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

@Serializable
data class SampleItem(
    val id: String,
    val title: String,
    val description: String? = null,
)

@Serializable
data class CreateItemRequest(
    val title: String,
    val description: String? = null,
)

interface ApiService {

    @GET("items")
    suspend fun getItems(
        @Query("limit") limit: Int = 20,
    ): List<SampleItem>

    @GET("items/{id}")
    suspend fun getItem(@Path("id") id: String): SampleItem

    @POST("items")
    suspend fun createItem(@Body body: CreateItemRequest): SampleItem
}

object Api {
    private const val BASE_URL = "https://example.com/api/"

    val service: ApiService by lazy {
        NetworkClient.create(BASE_URL, ApiService::class.java)
    }
}
