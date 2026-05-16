package com.example.hackerton.ui.navigation

import android.net.Uri

sealed class Route(val path: String) {
    data object Login : Route("login")
    data object Home : Route("home")

    data object Find : Route("find?query={query}") {
        const val ARG_QUERY = "query"
        fun build(query: String) = "find?query=${Uri.encode(query)}"
    }

    data object Share : Route("share/{itemId}") {
        const val ARG_ITEM_ID = "itemId"
        fun build(itemId: String) = "share/${Uri.encode(itemId)}"
    }

    data object Detail : Route("detail/{itemId}") {
        const val ARG_ITEM_ID = "itemId"
        fun build(itemId: String) = "detail/$itemId"
    }
}
