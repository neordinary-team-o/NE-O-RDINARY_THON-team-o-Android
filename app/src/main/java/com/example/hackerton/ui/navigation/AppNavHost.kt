package com.example.hackerton.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hackerton.ui.screens.detail.DetailScreen
import com.example.hackerton.ui.screens.home.HomeScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Route.Home.path,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Route.Home.path) {
            HomeScreen(
                onItemClick = { itemId ->
                    navController.navigate(Route.Detail.build(itemId))
                },
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
