package com.example.hackerton.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.hackerton.data.model.DigDetailResponse
import com.example.hackerton.data.repository.DigDetailResult
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
            val digId = itemId.toLongOrNull()
            val context = LocalContext.current
            val repo = remember(context) { SongRepository.get(context) }

            var detail by remember(digId) { mutableStateOf<DigDetailResponse?>(null) }

            LaunchedEffect(digId) {
                if (digId == null) return@LaunchedEffect
                when (val r = repo.getDig(digId)) {
                    is DigDetailResult.Success -> detail = r.data
                    is DigDetailResult.Error -> detail = null
                }
            }

            val fallback = painterResource(R.drawable.img_artist_placeholder)
            val thumbnailUrl = detail?.thumbnailUrl
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
                songTitle = detail?.title.orEmpty(),
                artist = detail?.artistName.orEmpty(),
                discoveryDate = formatDiscoveryDate(detail?.diggedAt),
                elapsedTime = detail?.elapsedMonths?.let { "${it}개월" }.orEmpty(),
                snapshotViewCount = detail?.viewCountAtDig,
                currentViewCount = detail?.currentViewCount,
                growthRate = detail?.growthRate,
                achievementBadge = detail?.achievementName,
                narrativeMessage = detail?.narrativeMessage,
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
    }.recoverCatching {
        java.time.LocalDate.parse(iso).format(DISCOVERY_DATE_FORMAT)
    }.getOrDefault("")
}
