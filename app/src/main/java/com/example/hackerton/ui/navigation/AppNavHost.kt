package com.example.hackerton.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil3.compose.rememberAsyncImagePainter
import com.example.hackerton.R
import com.example.hackerton.data.repository.SongRepository
import com.example.hackerton.ui.screens.detail.DetailScreen
import com.example.hackerton.ui.screens.find.FindScreen
import com.example.hackerton.ui.screens.home.HomeScreen
import com.example.hackerton.ui.screens.login.LoginScreen
import com.example.hackerton.ui.screens.share.ShareScreen
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.Login.path,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(Route.Login.path) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
            )
        }
        composable(Route.Home.path) {
            HomeScreen(
                onSearchSubmit = { q -> navController.navigate(Route.Find.build(q)) },
                onSongClick = { id -> navController.navigate(Route.Share.build(id)) },
                onAddClick = { navController.navigate(Route.Find.build("")) },
            )
        }
        composable(
            route = Route.Share.path,
            arguments = listOf(
                navArgument(Route.Share.ARG_ITEM_ID) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Route.Share.ARG_ITEM_ID).orEmpty()
            val context = LocalContext.current
            val repo = remember(context) { SongRepository.get(context) }
            val discoveredSongs by remember(repo) { repo.discoveredSongs }
                .collectAsState(initial = emptyList())
            // itemId는 HomeScreen에서 digId.toString(), FindScreen 시연용으론 videoId일 수 있음
            val song = discoveredSongs.find { it.digId?.toString() == itemId }
                ?: discoveredSongs.find { it.videoId == itemId }
            val fallback = painterResource(R.drawable.img_artist_placeholder)
            val thumbnailUrl = song?.thumbnailUrl
            val painter = if (thumbnailUrl.isNullOrBlank()) {
                fallback
            } else {
                rememberAsyncImagePainter(
                    model = thumbnailUrl,
                    placeholder = fallback,
                    error = fallback,
                    fallback = fallback,
                )
            }
            ShareScreen(
                songTitle = song?.title ?: itemId,
                artist = song?.artist ?: "",
                discoveryDate = formatDiscoveryDate(song?.discoveredAt),
                elapsedTime = formatElapsed(song?.discoveredAt),
                snapshotViewCount = song?.snapshotViewCount,
                currentViewCount = song?.currentViewCount,
                growthRate = song?.growthRate,
                achievementBadge = song?.achievementBadge,
                painter = painter,
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Route.Find.path,
            arguments = listOf(
                navArgument(Route.Find.ARG_QUERY) {
                    type = NavType.StringType
                    defaultValue = ""
                },
            ),
        ) { backStackEntry ->
            val q = backStackEntry.arguments?.getString(Route.Find.ARG_QUERY).orEmpty()
            FindScreen(
                initialQuery = q,
                onBack = { navController.popBackStack() },
            )
        }
        composable(
            route = Route.Detail.path,
            arguments = listOf(
                navArgument(Route.Detail.ARG_ITEM_ID) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Route.Detail.ARG_ITEM_ID).orEmpty()
            DetailScreen(
                itemId = itemId,
                onBack = { navController.popBackStack() },
            )
        }
    }
}

private val DISCOVERY_DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM.dd")

private fun formatDiscoveryDate(iso: String?): String {
    if (iso.isNullOrBlank()) return ""
    return runCatching {
        LocalDateTime.parse(iso).format(DISCOVERY_DATE_FORMAT)
    }.getOrDefault("")
}

private fun formatElapsed(iso: String?): String {
    if (iso.isNullOrBlank()) return ""
    return runCatching {
        val dug = LocalDateTime.parse(iso)
        val now = LocalDateTime.now()
        val years = java.time.temporal.ChronoUnit.YEARS.between(dug, now)
        if (years > 0) return "${years}년"
        val months = java.time.temporal.ChronoUnit.MONTHS.between(dug, now)
        if (months > 0) return "${months}개월"
        val days = java.time.temporal.ChronoUnit.DAYS.between(dug, now)
        if (days > 0) return "${days}일"
        val hours = java.time.temporal.ChronoUnit.HOURS.between(dug, now)
        if (hours > 0) return "${hours}시간"
        val minutes = java.time.temporal.ChronoUnit.MINUTES.between(dug, now)
        "${minutes.coerceAtLeast(1)}분"
    }.getOrDefault("")
}
