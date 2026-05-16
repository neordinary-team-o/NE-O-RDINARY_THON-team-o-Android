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
    // 발굴 등록 후 채워지는 필드들 (백엔드 DigResponse에서 가져옴)
    val discoveredAt: String? = null,
    val snapshotViewCount: Long? = null,
    val currentViewCount: Long? = null,
    val growthRate: Double? = null,
    val achievementBadge: String? = null,
)
