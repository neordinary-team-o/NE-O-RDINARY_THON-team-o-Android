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
// 🔥 새로 만든 튜토리얼 및 공유 화면 패키지 임포트
import com.example.hackerton.ui.screens.TutorialScreen
import com.example.hackerton.ui.screens.DetailShareScreen

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    // 💡 팁: 지금 당장 튜토리얼 화면 동작을 테스트하고 싶다면
    // 아래 주석을 풀고 startDestination = "tutorial" 로 지정하시면 앱이 튜토리얼로 켜집니다!
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
                    // 만약 로그인 완료 후 튜토리얼을 먼저 보여주고 싶다면
                    // 여기 Route.Home.path 대신 "tutorial"을 넣으셔도 됩니다!
                    navController.navigate(Route.Home.path) {
                        popUpTo(Route.Login.path) { inclusive = true }
                    }
                },
            )
        }

        // 🔥 1. 튜토리얼 가이드 노선 개통
        composable("tutorial") {
            TutorialScreen(
                onCloseTutorial = {
                    // 🎯 X 버튼 액션: 메인 홈 화면으로 다이렉트 점프 (뒤로가기로 튜토리얼 못 돌아오게 제거)
                    navController.navigate(Route.Home.path) {
                        popUpTo("tutorial") { inclusive = true }
                    }
                },
                onNextTutorial = {
                    // 🎯 화살표 버튼 액션: 발굴 성공 상세 공유 화면으로 슉 이동
                    navController.navigate("detail_share")
                }
            )
        }

        // 🔥 2. 발굴 성공 상세 공유 노선 개통
        composable("detail_share") {
            DetailShareScreen(
                onClose = {
                    // 🎯 X 버튼 액션: 다시 이전 튜토리얼 설명 페이지로 뒤로가기
                    navController.popBackStack()
                },
                onCheck = {
                    // 🎯 V 버튼 액션: 가이드가 끝났으니 최종 홈 화면으로 이동
                    navController.navigate(Route.Home.path) {
                        popUpTo("detail_share") { inclusive = true }
                    }
                }
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