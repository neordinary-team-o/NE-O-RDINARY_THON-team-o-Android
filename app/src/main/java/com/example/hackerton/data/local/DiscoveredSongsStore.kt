package com.example.hackerton.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.hackerton.data.model.SongSearchResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.discoveredSongsDataStore by preferencesDataStore("discovered_songs")

object DiscoveredSongsStore {
    private val KEY_JSON = stringPreferencesKey("songs_json")
    private val json = Json { ignoreUnknownKeys = true }

    fun observe(context: Context): Flow<List<SongSearchResponse>> =
        context.applicationContext.discoveredSongsDataStore.data.map { prefs ->
            decode(prefs[KEY_JSON])
        }

    /**
     * videoId 기준으로 upsert. 기존 entry가 있으면 새 데이터로 교체.
     */
    suspend fun add(context: Context, song: SongSearchResponse) {
        context.applicationContext.discoveredSongsDataStore.edit { prefs ->
            val current = decode(prefs[KEY_JSON])
            val others = current.filterNot { it.videoId == song.videoId }
            prefs[KEY_JSON] = json.encodeToString(others + song)
        }
    }

    private fun decode(raw: String?): List<SongSearchResponse> =
        raw?.let {
            runCatching { json.decodeFromString<List<SongSearchResponse>>(it) }
                .getOrDefault(emptyList())
        } ?: emptyList()
}
