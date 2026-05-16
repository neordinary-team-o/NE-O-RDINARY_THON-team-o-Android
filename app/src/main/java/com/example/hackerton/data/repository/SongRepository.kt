package com.example.hackerton.data.repository

import android.content.Context
import com.example.hackerton.data.local.DiscoveredSongsStore
import com.example.hackerton.data.model.DigDetailResponse
import com.example.hackerton.data.model.DigListResponse
import com.example.hackerton.data.model.DigRequest
import com.example.hackerton.data.model.SongSearchRequest
import com.example.hackerton.data.model.SongSearchResponse
import com.example.hackerton.data.network.Api
import com.example.hackerton.data.network.ApiService
import com.example.hackerton.util.DeviceId
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

sealed interface SearchResult {
    data class Success(val song: SongSearchResponse) : SearchResult
    data object NotFound : SearchResult
    data class Error(val message: String) : SearchResult
}

sealed interface DigResult {
    data object Success : DigResult
    data object AlreadyRegistered : DigResult
    data class Error(val message: String) : DigResult
}

sealed interface ListDigsResult {
    data class Success(val data: DigListResponse) : ListDigsResult
    data class Error(val message: String) : ListDigsResult
}

sealed interface DigDetailResult {
    data class Success(val data: DigDetailResponse) : DigDetailResult
    data class Error(val message: String) : DigDetailResult
}

/**
 * 곡 검색 / 발굴 / 발굴된 곡 목록을 단일 진입점으로 제공.
 * 화면은 이 Repository만 의존하고 ApiService / DataStore를 직접 다루지 않는다.
 */
class SongRepository private constructor(
    private val context: Context,
    private val api: ApiService,
) {
    val discoveredSongs: Flow<List<SongSearchResponse>> =
        DiscoveredSongsStore.observe(context)

    suspend fun search(keyword: String): SearchResult {
        val trimmed = keyword.trim()
        if (trimmed.isEmpty()) return SearchResult.NotFound
        return try {
            val resp = api.searchSong(SongSearchRequest(trimmed))
            when {
                resp.success && resp.data != null -> SearchResult.Success(resp.data)
                resp.success && resp.data == null -> SearchResult.NotFound
                else -> SearchResult.Error(resp.error?.message ?: "검색 결과가 없습니다.")
            }
        } catch (e: HttpException) {
            if (e.code() == 404) SearchResult.NotFound
            else SearchResult.Error(e.message ?: "검색 실패")
        } catch (e: Exception) {
            SearchResult.Error(e.message ?: "네트워크 오류")
        }
    }

    suspend fun dig(song: SongSearchResponse): DigResult {
        val discoveredAt = LocalDateTime.now()
            .truncatedTo(ChronoUnit.SECONDS)
            .toString()
        // 우선 로컬에 기록 (오프라인이거나 API 실패해도 화면에는 반영)
        DiscoveredSongsStore.add(context, song.copy(discoveredAt = discoveredAt))
        return try {
            val resp = api.registerDig(
                DigRequest(
                    userId = DeviceId.userId(context),
                    videoId = song.videoId,
                    title = song.title,
                    artist = song.artist,
                    viewCount = song.viewCount,
                    uploadDate = song.uploadDate,
                    thumbnailUrl = song.thumbnailUrl,
                    comment = "",
                )
            )
            resp.data?.let { dig ->
                DiscoveredSongsStore.add(
                    context,
                    song.copy(
                        discoveredAt = dig.dugAt ?: discoveredAt,
                        snapshotViewCount = dig.snapshotViewCount,
                        currentViewCount = dig.currentViewCount,
                        growthRate = dig.growthRate,
                        achievementBadge = dig.achievementBadge,
                        digId = dig.digId,
                    ),
                )
            }
            DigResult.Success
        } catch (e: HttpException) {
            if (e.code() == 409) DigResult.AlreadyRegistered
            else DigResult.Error(e.message ?: "발굴 등록 실패")
        } catch (e: Exception) {
            DigResult.Error(e.message ?: "네트워크 오류")
        }
    }

    suspend fun listDigs(page: Int): ListDigsResult {
        return try {
            val userId = DeviceId.userId(context)
            val resp = api.listDigs(userId = userId, page = page)
            if (resp.success && resp.data != null) {
                ListDigsResult.Success(resp.data)
            } else {
                ListDigsResult.Error(resp.error?.message ?: "발굴 목록 조회 실패")
            }
        } catch (e: HttpException) {
            ListDigsResult.Error(e.message ?: "발굴 목록 조회 실패")
        } catch (e: Exception) {
            ListDigsResult.Error(e.message ?: "네트워크 오류")
        }
    }

    suspend fun getDig(digId: Long): DigDetailResult {
        return try {
            val resp = api.getDig(digId)
            if (resp.success && resp.data != null) {
                DigDetailResult.Success(resp.data)
            } else {
                DigDetailResult.Error(resp.error?.message ?: "발굴 상세 조회 실패")
            }
        } catch (e: HttpException) {
            DigDetailResult.Error(e.message ?: "발굴 상세 조회 실패")
        } catch (e: Exception) {
            DigDetailResult.Error(e.message ?: "네트워크 오류")
        }
    }

    companion object {
        @Volatile
        private var instance: SongRepository? = null

        fun get(context: Context): SongRepository =
            instance ?: synchronized(this) {
                instance ?: SongRepository(
                    context.applicationContext,
                    Api.service,
                ).also { instance = it }
            }
    }
}
