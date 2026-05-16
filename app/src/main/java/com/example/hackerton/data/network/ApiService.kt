package com.example.hackerton.data.network

import com.example.hackerton.data.model.ApiResponse
import com.example.hackerton.data.model.SongSearchRequest
import com.example.hackerton.data.model.SongSearchResponse
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

    @POST("songs/search")
    suspend fun searchSong(@Body body: SongSearchRequest): ApiResponse<SongSearchResponse>
}

object Api {
    private const val BASE_URL = "https://api.neordinary-o.r-e.kr/api/"

    val service: ApiService by lazy {
        NetworkClient.create(BASE_URL, ApiService::class.java)
    }
}
