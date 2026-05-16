package com.example.hackerton.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DigDetailResponse(
    val thumbnailUrl: String? = null,
    val title: String? = null,
    val artistName: String? = null,
    val diggedAt: String? = null,
    val elapsedMonths: Int? = null,
    val achievementName: String? = null,
    val viewCountAtDig: Long? = null,
    val currentViewCount: Long? = null,
    val growthRate: Double? = null,
    val narrativeMessage: String? = null,
)
