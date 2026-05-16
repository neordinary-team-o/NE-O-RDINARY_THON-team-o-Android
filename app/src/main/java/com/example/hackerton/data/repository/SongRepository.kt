package com.example.hackerton.data.repository

import android.content.Context
import android.util.Log
import com.example.hackerton.data.local.DiscoveredSongsStore
import com.example.hackerton.data.model.DigDetailResponse
import com.example.hackerton.data.model.DigListItem
import com.example.hackerton.data.model.DigListResponse
import com.example.hackerton.data.model.DigRequest
import com.example.hackerton.data.model.SongSearchRequest
import com.example.hackerton.data.model.SongSearchResponse
import com.example.hackerton.data.network.Api
import com.example.hackerton.data.network.ApiService
import com.example.hackerton.util.DeviceId
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

sealed interface SearchMyDigsResult {
    data class Success(val items: List<DigListItem>) : SearchMyDigsResult
    data class Error(val message: String) : SearchMyDigsResult
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

    // 발굴 성공 후 HomeScreen 등 외부 화면이 리스트를 다시 받아오도록 알리는 신호.
    // 값 자체엔 의미 없고 변경 자체가 트리거.
    private val _digsInvalidated = MutableStateFlow(0)
    val digsInvalidated: StateFlow<Int> = _digsInvalidated.asStateFlow()

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
        val userId = DeviceId.userId(context)
        Log.d(TAG, "dig() request userId=$userId videoId=${song.videoId} title=${song.title}")
        return try {
            val resp = api.registerDig(
                DigRequest(
                    userId = userId,
                    videoId = song.videoId,
                    title = song.title,
                    artist = song.artist,
                    viewCount = song.viewCount,
                    uploadDate = song.uploadDate,
                    thumbnailUrl = song.thumbnailUrl,
                    comment = "",
                )
            )
            Log.d(TAG, "dig() response success=${resp.success} digId=${resp.data?.digId} dugAt=${resp.data?.dugAt} error=${resp.error}")
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
            _digsInvalidated.value = _digsInvalidated.value + 1
            Log.d(TAG, "dig() invalidated digs, new signal=${_digsInvalidated.value}")
            DigResult.Success
        } catch (e: HttpException) {
            Log.e(TAG, "dig() HttpException code=${e.code()} msg=${e.message()}", e)
            if (e.code() == 409) DigResult.AlreadyRegistered
            else DigResult.Error(e.message ?: "발굴 등록 실패")
        } catch (e: Exception) {
            Log.e(TAG, "dig() exception", e)
            DigResult.Error(e.message ?: "네트워크 오류")
        }
    }

    suspend fun listDigs(page: Int): ListDigsResult {
        return try {
            val userId = DeviceId.userId(context)
            Log.d(TAG, "listDigs() request userId=$userId page=$page")
            val resp = api.listDigs(userId = userId, page = page)
            Log.d(
                TAG,
                "listDigs() response success=${resp.success} page=${resp.data?.page} totalPages=${resp.data?.totalPages} totalElements=${resp.data?.totalElements} contentSize=${resp.data?.content?.size} ids=${resp.data?.content?.map { it.digId }} error=${resp.error}",
            )
            if (resp.success && resp.data != null) {
                ListDigsResult.Success(resp.data)
            } else {
                ListDigsResult.Error(resp.error?.message ?: "발굴 목록 조회 실패")
            }
        } catch (e: HttpException) {
            Log.e(TAG, "listDigs() HttpException code=${e.code()} msg=${e.message()}", e)
            ListDigsResult.Error(e.message ?: "발굴 목록 조회 실패")
        } catch (e: Exception) {
            Log.e(TAG, "listDigs() exception", e)
            ListDigsResult.Error(e.message ?: "네트워크 오류")
        }
    }

    suspend fun searchMyDigs(keyword: String): SearchMyDigsResult {
        val trimmed = keyword.trim()
        if (trimmed.isEmpty()) return SearchMyDigsResult.Success(emptyList())
        return try {
            val userId = DeviceId.userId(context)
            Log.d(TAG, "searchMyDigs() userId=$userId keyword=$trimmed")
            val resp = api.searchMyDigs(userId = userId, keyword = trimmed)
            Log.d(
                TAG,
                "searchMyDigs() success=${resp.success} size=${resp.data?.size} ids=${resp.data?.map { it.digId }} error=${resp.error}",
            )
            // error 필드가 채워졌으면 실패로 간주 (사양상 200이어도 error 있을 수 있음)
            if (resp.error != null) {
                SearchMyDigsResult.Error(resp.error.message)
            } else {
                SearchMyDigsResult.Success(resp.data.orEmpty())
            }
        } catch (e: HttpException) {
            Log.e(TAG, "searchMyDigs() HttpException code=${e.code()}", e)
            SearchMyDigsResult.Error(e.message ?: "검색 실패")
        } catch (e: Exception) {
            Log.e(TAG, "searchMyDigs() exception", e)
            SearchMyDigsResult.Error(e.message ?: "네트워크 오류")
        }
    }

    suspend fun getDig(digId: Long): DigDetailResult {
        // 1) 성장률 재계산 트리거를 먼저 쏘고 (실패해도 detail은 계속 시도)
        runCatching {
            val gr = api.refreshGrowthRate(digId)
            Log.d(TAG, "refreshGrowthRate($digId) success=${gr.success} error=${gr.error}")
        }.onFailure { e ->
            // 404/500이면 HttpException으로 떨어짐 — detail에서 다시 핸들링하게 두고 로그만
            Log.w(TAG, "refreshGrowthRate($digId) failed: ${e.message}")
        }
        // 2) 최신 detail 가져오기
        return try {
            val resp = api.getDig(digId)
            Log.d(TAG, "getDig($digId) success=${resp.success} error=${resp.error}")
            if (resp.success && resp.data != null) {
                DigDetailResult.Success(resp.data)
            } else {
                DigDetailResult.Error(resp.error?.message ?: "발굴 상세 조회 실패")
            }
        } catch (e: HttpException) {
            Log.e(TAG, "getDig($digId) HttpException code=${e.code()}", e)
            DigDetailResult.Error(e.message ?: "발굴 상세 조회 실패")
        } catch (e: Exception) {
            Log.e(TAG, "getDig($digId) exception", e)
            DigDetailResult.Error(e.message ?: "네트워크 오류")
        }
    }

    companion object {
        private const val TAG = "SongRepository"

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
