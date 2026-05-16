package com.example.hackerton.data.network

import com.example.hackerton.data.model.ApiResponse
import com.example.hackerton.data.model.DigDetailResponse
import com.example.hackerton.data.model.DigListResponse
import com.example.hackerton.data.model.DigRequest
import com.example.hackerton.data.model.DigResponse
import com.example.hackerton.data.model.SongSearchRequest
import com.example.hackerton.data.model.SongSearchResponse
import kotlinx.serialization.Serializable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @POST("digs")
    suspend fun registerDig(@Body body: DigRequest): ApiResponse<DigResponse>

    @GET("digs")
    suspend fun listDigs(
        @Query("userId") userId: String,
        @Query("page") page: Int,
    ): ApiResponse<DigListResponse>

    @GET("digs/{digId}")
    suspend fun getDig(@Path("digId") digId: Long): ApiResponse<DigDetailResponse>

    /**
     * 성장률 재계산 트리거 — 응답 data는 null. detail 조회 직전에 호출.
     * 404: dig 없음, 500: YouTube API 실패.
     */
    @PATCH("digs/{digId}/growth-rate")
    suspend fun refreshGrowthRate(@Path("digId") digId: Long): ApiResponse<String?>

    @GET("digs/me/search")
    suspend fun searchMyDigs(
        @Query("userId") userId: String,
        @Query("keyword") keyword: String,
    ): ApiResponse<List<com.example.hackerton.data.model.DigListItem>>
}

object Api {
    private const val BASE_URL = "https://api.neordinary-o.r-e.kr/api/"

    val service: ApiService by lazy {
        NetworkClient.create(BASE_URL, ApiService::class.java)
    }
}
