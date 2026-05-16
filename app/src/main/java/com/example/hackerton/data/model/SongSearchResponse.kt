package com.example.hackerton.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SongSearchRequest(
    val keyword: String,
)

@Serializable
data class SongSearchResponse(
    val videoId: String,
    val title: String,
    val artist: String,
    val viewCount: Long,
    val uploadDate: String,
    val thumbnailUrl: String,
    val discoveredAt: String? = null,
)
