package com.example.hackerton.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.HackertonTheme

/**
 * [SplashScreen]
 * 배경을 새롭게 교체한 splash_background.png 이미지로 꽉 채우고 중앙에 로고를 배치한 화면
 */
@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // 1. 배경 이미지 (.png 파일을 자동으로 인식해서 바닥에 깔아줍니다)
        Image(
            painter = painterResource(id = R.drawable.splash_background),
            contentDescription = null,
            contentScale = ContentScale.Crop, // 화면 비율이 달라도 여백 없이 꽉 채우도록 설정
            modifier = Modifier.fillMaxSize()
        )

        // 2. 중앙 타이틀 로고
        Image(
            painter = painterResource(id = R.drawable.logo_hongdae),
            contentDescription = "홍대병동 로고",
            modifier = Modifier.width(200.dp)
        )
    }
}

// ==========================================================================
// 👁️ 스플래시 화면 프리뷰
// ==========================================================================
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SplashScreenPreview() {
    HackertonTheme {
        SplashScreen()
    }
}