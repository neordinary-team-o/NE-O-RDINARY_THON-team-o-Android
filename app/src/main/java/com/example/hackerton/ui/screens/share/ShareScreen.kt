package com.example.hackerton.ui.screens.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.unit.sp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.Caption
import com.example.hackerton.ui.theme.Gray200
import com.example.hackerton.ui.theme.Gray300
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenNormal
import com.example.hackerton.ui.theme.LabelNormal
import com.example.hackerton.ui.theme.LabelReading
import com.example.hackerton.ui.theme.Title

@Composable
fun ShareScreen(
    songTitle: String,
    artist: String,
    discoveryDate: String,
    elapsedTime: String,
    growthRate: String,
    painter: Painter,
    onBack: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.image_9),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xE6111111)),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
        ) {
            // Header: ← + "발굴 성공!"
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로",
                        tint = GrayWhite,
                    )
                }
                Image(
                    painter = painterResource(R.drawable.dig_success_logo),
                    contentDescription = "발굴 성공!",
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            Spacer(Modifier.height(55.dp))

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(20.dp)),
            ) {
                Image(
                    painter = painter,
                    contentDescription = songTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
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

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    "발굴일",
                    style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = Gray300),
                )
                Text(
                    discoveryDate,
                    style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = Gray300),
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "$elapsedTime 경과",
                style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = GreenNormal),
                modifier = Modifier.align(Alignment.CenterHorizontally),
            )

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    "당신은",
                    style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = Gray300),
                )
                Text(
                    "Trend Catcher!",
                    style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = GreenNormal),
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = buildAnnotatedString {
                    append("당신이 12,400명이 듣던 시절 발견한 음악이 지금 ")
                    withStyle(SpanStyle(color = GreenNormal)) { append("5,800만명") }
                    append("에게 재생되고 있습니다. 당신의 귀는 시대보다 ")
                    withStyle(SpanStyle(color = GreenNormal)) { append(elapsedTime) }
                    append(" 빨랐습니다.")
                },
                style = LabelReading.copy(fontWeight = FontWeight.SemiBold, color = Gray200),
                modifier = Modifier.padding(horizontal = 20.dp),
            )

            Spacer(Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
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
                    Text(
                        text = growthRate,
                        style = Title.copy(
                            fontSize = 32.sp,
                            lineHeight = 42.sp,
                            fontWeight = FontWeight.Bold,
                            color = GreenNormal,
                        ),
                    )
                    Text(
                        "성장률",
                        style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = GreenNormal),
                    )
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ShareScreenPreview() {
    ShareScreen(
        songTitle = "0+0",
        artist = "한로로",
        discoveryDate = "24.03.15",
        elapsedTime = "8개월",
        growthRate = "+4.723%",
        painter = painterResource(R.drawable.artist_big),
        onBack = {},
    )
}
