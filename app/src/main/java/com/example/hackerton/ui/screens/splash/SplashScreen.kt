package com.example.hackerton.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.HackertonTheme

/**
 * [SplashScreen]
 * 시안에 맞춰 R.drawable.logo_hongdae (XML 벡터)의 크기를 큼직하게 키운 스플래시 화면
 */
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack), // 홍대병동 고유의 다크 배경 토큰
        contentAlignment = Alignment.Center
    ) {
        // 🔥 wrapContentSize() 대신 width()를 줘서 XML 벡터 로고를 확 키웁니다.
        // 프리뷰를 보시고 너무 크거나 작으면 200.dp 값을 180.dp나 240.dp 등으로 살짝씩 조율해 보세요!
        Image(
            painter = painterResource(id = R.drawable.logo_hongdae),
            contentDescription = "홍대병동 로고",
            modifier = Modifier.width(200.dp)
        )
    }
}

// ==========================================================================
// 👁️ 스플래시 화면 프리뷰 (실제 스마트폰 시스템 UI 포함 컴포즈 뷰)
// ==========================================================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    HackertonTheme {
        SplashScreen()
    }
}