package com.example.hackerton.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.compose.ui.res.painterResource
import com.example.hackerton.R
import com.example.hackerton.ui.components.ShareModal
import com.example.hackerton.ui.screens.detail.DetailScreen
import com.example.hackerton.ui.screens.find.FindScreen
import com.example.hackerton.ui.screens.home.HomeScreen
import com.example.hackerton.ui.screens.login.LoginScreen

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
            )
        }
        composable(
            route = Route.Share.path,
            arguments = listOf(
                navArgument(Route.Share.ARG_ITEM_ID) { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString(Route.Share.ARG_ITEM_ID).orEmpty()
            ShareModal(
                songTitle = itemId,
                artist = "한로로",
                discoveryDate = "24.03.15",
                elapsedTime = "8개월",
                growthRate = "+4,723%",
                painter = painterResource(R.drawable.artist_big),
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
