package com.example.hackerton.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.dp
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.GreenNormal

/**
 * 앱 공통 배경: GrayBlack 베이스 + 상단 중앙에서 퍼지는 GreenNormal 라디얼 그라데이션.
 *
 * Figma 사양(node 197:7255, 360x249 박스):
 *  - 중심: 가로 정중앙, 세로 y≈8 (거의 박스 상단)
 *  - 타원: 가로 반경≈262, 세로 반경≈181 (가로가 1.45배 넓음)
 *  - 색: GreenNormal 1.0 → 0.0, 전체 opacity 0.15
 * Compose 라디얼은 정원만 지원하므로 canvas scaleX로 타원 효과를 낸다.
 */
@Composable
fun BrandGradientBackground(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(249.dp)
                .align(Alignment.TopCenter)
                .drawBehind {
                    val cx = size.width / 2f
                    val cy = 8.dp.toPx()
                    val rx = size.width * (262f / 360f)
                    val ry = size.height * (181f / 249f)
                    scale(scaleX = rx / ry, scaleY = 1f, pivot = Offset(cx, cy)) {
                        drawCircle(
                            brush = Brush.radialGradient(
                                colors = listOf(
                                    GreenNormal.copy(alpha = 0.15f),
                                    GreenNormal.copy(alpha = 0f),
                                ),
                                center = Offset(cx, cy),
                                radius = ry,
                            ),
                            radius = ry,
                            center = Offset(cx, cy),
                        )
                    }
                }
        )
        content()
    }
}
