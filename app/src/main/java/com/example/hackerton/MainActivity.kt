package com.example.hackerton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.* // 🔥 상태 관리를 위해 추가
import com.example.hackerton.ui.navigation.AppNavHost
import com.example.hackerton.ui.screens.SplashScreen // 🔥 우리가 만든 스플래시 임포트
import com.example.hackerton.ui.theme.HackertonTheme
import kotlinx.coroutines.delay // 🔥 타이머를 위해 추가

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HackertonTheme {
                // 1. 스플래시 화면 표시 여부를 쥐고 있는 런타임 상태
                var showSplashScreen by remember { mutableStateOf(true) }

                // 2. 2초(2000ms) 동안 대기 후 상태를 끄는(false) 타이머 루틴
                LaunchedEffect(key1 = Unit) {
                    delay(2000) // 2초 뒤에
                    showSplashScreen = false // 스플래시 종료!
                }

                // 3. 상태에 따라 화면 스위칭
                if (showSplashScreen) {
                    // 처음 2초 동안은 무조건 이 거대한 로고 화면을 보여줍니다.
                    SplashScreen()
                } else {
                    // 2초가 지나면 팀원분이 세팅해둔 네비게이션 메인 줄기로 부드럽게 넘겨줍니다.
                    AppNavHost()
                }
            }
        }
    }
}