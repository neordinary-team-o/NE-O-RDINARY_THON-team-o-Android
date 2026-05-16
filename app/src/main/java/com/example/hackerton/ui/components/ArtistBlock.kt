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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hackerton.R
import com.example.hackerton.ui.theme.Gray900
import com.example.hackerton.ui.theme.GrayBlack

/**
 * 1. 큰 아티스트 블록 (artist_big)
 */
@Composable
fun ArtistBigBlock(
    name: String,
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tintOverlayColor: Color = Color.Black.copy(alpha = 0.3f),
) {
    Box(
        modifier = modifier
            .aspectRatio(1f) // 정사각형 가변 비율 유지
            .clip(RoundedCornerShape(24.dp)) // 큰 블록용 라운드
            .clickable { onClick() }
    ) {
        Image(
            painter = painter,
            contentDescription = "$name 프로필",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        // 하단 텍스트 가독성을 위한 그라데이션 오버레이
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
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = GrayWhite
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
        )
    }
}

/**
 * 2. 작은 아티스트 블록 (artist_small)
 */
@Composable
fun ArtistSmallBlock(
    name: String,
    painter: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tintOverlayColor: Color = Color.Black.copy(alpha = 0.3f),
) {
    Box(
        modifier = modifier
            .aspectRatio(1f) // 정사각형 가변 비율 유지
            .clip(RoundedCornerShape(16.dp)) // 작은 블록용 라운드
            .clickable { onClick() }
    ) {
        Image(
            painter = painter,
            contentDescription = "$name 프로필",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
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
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold,
                color = GrayWhite
            ),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )
    }
}

/**
 * 3. 빈 큰 블록 (slot이 비었을 때 자리만 유지)
 */
@Composable
fun ArtistBigBlockEmpty(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(24.dp))
            .background(Gray900)
    )
}

/**
 * 4. 빈 작은 블록 (slot이 비었을 때 자리만 유지)
 */
@Composable
fun ArtistSmallBlockEmpty(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(Gray900)
    )
}

@Preview(name = "Separated Artist Blocks Preview", showBackground = true)
@Composable
fun ArtistBlocksPreview() {
    val dummyPainter = painterResource(id = R.drawable.arrow_icon)

    Column(
        modifier = Modifier
            .background(GrayBlack)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(text = "Artist Big", color = GrayWhite)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 이제 그냥 깔끔하게 함수 이름으로 호출 끝!
            ArtistBigBlock(name = "한로로", painter = dummyPainter, onClick = {}, modifier = Modifier.weight(1f))
            ArtistBigBlock(name = "한로로", painter = dummyPainter, onClick = {}, modifier = Modifier.weight(1f))
        }

        Text(text = "Artist Small", color = GrayWhite)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ArtistSmallBlock(name = "10cm", painter = dummyPainter, onClick = {}, modifier = Modifier.weight(1f))
            ArtistSmallBlock(name = "10cm", painter = dummyPainter, onClick = {}, modifier = Modifier.weight(1f))
            ArtistSmallBlock(name = "10cm", painter = dummyPainter, onClick = {}, modifier = Modifier.weight(1f))
        }
    }
}