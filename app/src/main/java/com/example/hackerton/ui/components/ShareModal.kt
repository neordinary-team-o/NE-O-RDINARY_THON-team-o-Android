package com.example.hackerton.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.Caption
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.Gray200
import com.example.hackerton.ui.theme.Gray300
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenNormal
import com.example.hackerton.ui.theme.Headline
import com.example.hackerton.ui.theme.LabelNormal
import com.example.hackerton.ui.theme.LabelReading
import com.example.hackerton.ui.theme.Title

@Composable
fun ShareModal(
    songTitle: String,
    artist: String,
    discoveryDate: String,
    elapsedTime: String,
    growthRate: String,
    painter: Painter,
    onShareClick: () -> Unit,
    onDismiss: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xA8151515))
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) { onDismiss() },
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .width(322.dp)
                .clip(RoundedCornerShape(30.dp))
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) { },
        ) {
            // 배경 이미지
            Image(
                painter = painterResource(R.drawable.image_9),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize(),
            )
            // 다크 오버레이
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(Color(0xE6111111)),
            )
            // 콘텐츠
            Column(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(top = 24.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ) {
            // Header: 발굴 성공! 로고 + X
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.dig_success_logo),
                    contentDescription = "발굴 성공!",
                    modifier = Modifier.align(Alignment.Center),
                )
                Icon(
                    painter = painterResource(R.drawable.x_icon),
                    contentDescription = "닫기",
                    tint = GrayWhite,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(24.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() },
                        ) { onDismiss() },
                )
            }

            // Thumbnail + song info
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(20.dp)),
                ) {
                    Image(
                        painter = painter,
                        contentDescription = songTitle,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(2.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text(
                            text = songTitle,
                            style = Title.copy(fontWeight = FontWeight.Bold, color = GrayWhite),
                        )
                        Text(
                            text = artist,
                            style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = Gray300),
                        )
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text("발굴일", style = Caption.copy(color = Gray300))
                        Text(discoveryDate, style = Caption.copy(color = Gray300))
                    }
                }
            }

            // Trend badge
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Trend Catcher", style = Caption.copy(color = GreenNormal))
                Text("홍대병동님의 발견", style = Caption.copy(color = Gray300))
            }

            // Description + stats + CTA
            Column(verticalArrangement = Arrangement.spacedBy(32.dp)) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    Text(
                        text = buildAnnotatedString {
                            append("당신이 12,400명이 듣던 시절 발견한 음악이 지금 ")
                            withStyle(SpanStyle(color = GreenNormal)) { append("5,800만명") }
                            append("에게 재생되고 있습니다. 당신의 귀는 시대보다 ")
                            withStyle(SpanStyle(color = GreenNormal)) { append(elapsedTime) }
                            append(" 빨랐습니다.")
                        },
                        style = LabelReading.copy(fontWeight = FontWeight.SemiBold, color = Gray200),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("당시 조회수", style = Caption.copy(color = Gray300))
                                Text("14,205회", style = Caption.copy(color = Gray300))
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("현재 조회수", style = Caption.copy(color = GrayWhite))
                                Text("18,891회", style = Caption.copy(color = GrayWhite))
                            }
                        }
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                        ) {
                            Text("성장률", style = Caption.copy(color = GreenNormal))
                            Text(
                                text = growthRate,
                                style = Title.copy(fontWeight = FontWeight.Bold, color = GreenNormal),
                            )
                        }
                    }
                }

                // Share CTA
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(1000.dp))
                        .background(GreenNormal)
                        .clickable { onShareClick() }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "공유하기",
                        style = Headline.copy(fontWeight = FontWeight.SemiBold, color = GrayBlack),
                    )
                }
            }
            }  // Column
        }  // card Box
    }  // outer Box
}

@Preview(showBackground = true)
@Composable
private fun ShareModalPreview() {
    ShareModal(
        songTitle = "0+0",
        artist = "한로로",
        discoveryDate = "24.03.15",
        elapsedTime = "8개월",
        growthRate = "+4,723%",
        painter = painterResource(R.drawable.arrow_icon),
        onShareClick = {},
        onDismiss = {},
    )
}
