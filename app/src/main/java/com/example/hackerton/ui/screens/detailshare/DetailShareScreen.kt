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
fun DetailShareScreen(
    onClose: () -> Unit = {},
    onCheck: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack),
        contentAlignment = Alignment.TopCenter
    ) {
        // 1. 배경 이미지 (detail_share.jpg 연동)
        Image(
            painter = painterResource(id = R.drawable.detail_share),
            contentDescription = "발굴 성공 공유 배경 화면",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxWidth()
        )

        // ------------------------------------------------------------------
        // 2. [상단 인터랙션 레이어]
        // 🛠️ 핵심 수정: 버튼들을 "발굴 성공!" 텍스트 높이에 맞춥니다.
        // ------------------------------------------------------------------
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 80.dp) // 🔥 시안의 "발굴 성공!" 글자 중앙 높이까지 묵직하게 내려줍니다! (기존 44dp -> 80dp)
                .padding(horizontal = 4.dp), // IconButton 자체 패딩을 고려해 양 끝 여백을 4.dp로 조절 🎯
            horizontalArrangement = Arrangement.SpaceBetween, // 좌우 끝으로 배치
            verticalAlignment = Alignment.CenterVertically // 버튼 자체를 수직 중앙 정렬
        ) {
            // 좌측 X (닫기) 버튼: grayclose_icon 적용
            IconButton(onClick = onClose) {
                Icon(
                    painter = painterResource(id = R.drawable.grayclose_icon),
                    contentDescription = "닫기",
                    tint = GrayWhite,
                    modifier = Modifier.size(24.dp)
                )
            }

            // 우측 V (체크) 버튼: graycheck_icon 적용
            IconButton(onClick = onCheck) {
                Icon(
                    painter = painterResource(id = R.drawable.graycheck_icon),
                    contentDescription = "확인",
                    tint = GrayWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun DetailShareScreenPreview() {
    HackertonTheme {
        DetailShareScreen()
    }
}