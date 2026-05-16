package com.example.hackerton.ui.navigation

sealed class Route(val path: String) {
    data object Home : Route("home")

    data object Detail : Route("detail/{itemId}") {
        const val ARG_ITEM_ID = "itemId"
        fun build(itemId: String) = "detail/$itemId"
    }
}
