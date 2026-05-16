package com.example.hackerton.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DigListResponse(
    val content: List<DigListItem> = emptyList(),
    val page: Int = 0,
    val size: Int = 0,
    val totalElements: Long = 0L,
    val totalPages: Int = 0,
)

@Serializable
data class DigListItem(
    val digId: Long,
    val title: String,
    val thumbnailUrl: String,
)
