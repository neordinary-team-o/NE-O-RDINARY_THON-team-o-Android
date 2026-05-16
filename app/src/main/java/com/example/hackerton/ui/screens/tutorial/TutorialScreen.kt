package com.example.hackerton.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.*

@Composable
fun TutorialScreen(
    onCloseTutorial: () -> Unit = {},
    onNextTutorial: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack),
        contentAlignment = Alignment.TopCenter
    ) {
        // 1. 배경 앱 캡처 이미지 (상태바, 타이틀, 말풍선이 이미 포함된 원본 프레임)
        Image(
            painter = painterResource(id = R.drawable.tutorial_frame),
            contentDescription = "튜토리얼 배경 화면",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        // ------------------------------------------------------------------
        // 2. [상단 인터랙션 레이어]
        // 이미지 속 텍스트 위치(iOS 표준 상단바 44dp + 헤더 56dp)에 정확히 맞춘 영역
        // ------------------------------------------------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 44.dp) // 이미지 내부의 9:41 상태바 높이만큼 정확히 내려줍니다.
                .height(56.dp) // 홍대병동 타이틀이 위치한 헤더 정렬 높이
        ) {
            // 좌측 X (닫기) 버튼
            IconButton(
                onClick = onCloseTutorial,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.grayclose_icon),
                    contentDescription = "튜토리얼 닫기",
                    tint = GrayWhite,
                    modifier = Modifier.size(24.dp)
                )
            }

            // 우측 화살표 (다음) 버튼
            IconButton(
                onClick = onNextTutorial,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.grayarrow_icon),
                    contentDescription = "다음 튜토리얼 가이드",
                    tint = GrayWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun TutorialScreenPreview() {
    HackertonTheme {
        TutorialScreen()
    }
}