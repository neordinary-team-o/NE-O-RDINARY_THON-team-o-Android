package com.example.hackerton.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hackerton.ui.theme.GrayWhite

/**
 * 아티스트 블록의 크기 유형 정의 (Big / Small)
 */
enum class ArtistBlockSize { Big, Small }

@Composable
fun ArtistBlock(
    name: String,
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: ArtistBlockSize = ArtistBlockSize.Big,
    // 디자이너 시안의 불투명도/색상 필터 상태 변화에 대응하기 위한 변수
    tintOverlayColor: Color = Color.Black.copy(alpha = 0.3f)
) {
    // 1. 디자이너 시안 기준 크기별 라운드(Corner Radius) 분기
    val cornerRadius = if (size == ArtistBlockSize.Big) 24.dp else 16.dp

    // 2. 크기별 텍스트 스타일 및 내부 패딩 분기
    val textStyle = if (size == ArtistBlockSize.Big) {
        MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = GrayWhite)
    } else {
        MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = GrayWhite)
    }
    val internalPadding = if (size == ArtistBlockSize.Big) 16.dp else 12.dp

    Box(
        modifier = modifier
            .aspectRatio(1f) // 🔥 핵심: 하드코딩 크기 없이 무조건 1:1 정사각형 비율을 강제하여 가변성 확보
            .clip(RoundedCornerShape(cornerRadius))
            .clickable { onClick() }
    ) {
        // [레이어 1] 아티스트 이미지 (전체 채우기)
        Image(
            painter = painter,
            contentDescription = "$name 프로필 이미지",
            contentScale = ContentScale.Crop, // 찌그러짐 없이 꽉 채우기
            modifier = Modifier.fillMaxSize()
        )

        // [레이어 2] 딤(Dim) 및 컬러 필터 오버레이
        // 하단 흰색 글씨 가독성을 위해 아래로 갈수록 어두워지는 그라데이션 적용
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            tintOverlayColor.copy(alpha = tintOverlayColor.alpha * 0.3f),
                            tintOverlayColor.copy(alpha = tintOverlayColor.alpha * 1.2f)
                        )
                    )
                )
        )

        // [레이어 3] 아티스트 이름 (좌측 하단 고정)
        Text(
            text = name,
            style = textStyle,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(internalPadding)
        )
    }
}