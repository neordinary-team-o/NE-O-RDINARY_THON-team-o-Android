package com.example.hackerton.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DigRequest(
    val userId: String,
    val videoId: String,
    val title: String,
    val artist: String,
    val viewCount: Long,
    val uploadDate: String,
    val thumbnailUrl: String,
    val comment: String,
)

@Serializable
data class DigResponse(
    val digId: Long? = null,
    val songId: Long? = null,
    val title: String? = null,
    val artist: String? = null,
    val thumbnailUrl: String? = null,
    val snapshotViewCount: Long? = null,
    val currentViewCount: Long? = null,
    val growthRate: Double? = null,
    val achievementBadge: String? = null,
    val dugAt: String? = null,
)
