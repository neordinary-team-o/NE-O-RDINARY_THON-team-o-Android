package com.example.hackerton.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.*

@Composable
fun DigInfoCard(
    title: String,
    artist: String,
    description: String,
    growthRate: String,
    discoveryDate: String,
    elapsedTime: String,
    onShareClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Gray900.copy(alpha = 0.65f))
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // [상단 레이아웃] 곡명 + 아티스트명 및 우측 컨트롤 버튼 셋
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 텍스트 영역 묶음 (버튼 영역을 침범하지 않도록 weight(1f) 부여)
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GrayWhite
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = artist,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = Gray300
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f, fill = false)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            // 우측 아이콘 버튼 세트 (공유, 닫기)
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        // 🔥 기존 내장 라이브러리 대신 drawable의 share_icon을 로드합니다.
                        painter = painterResource(id = R.drawable.share_icon),
                        contentDescription = "공유하기",
                        tint = GrayWhite // 디자인 시스템에 맞는 흰색 틴트 유지
                    )
                }
                IconButton(onClick = onCloseClick) {
                    Icon(
                        // 🔥 기존 내장 라이브러리 대신 drawable의 x_icon을 로드합니다.
                        painter = painterResource(id = R.drawable.x_icon),
                        contentDescription = "닫기",
                        tint = GrayWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // [중단 레이아웃] 서사형 문구 및 유저 코멘트
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp,
                color = Gray200
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        // [하단 레이아웃] 실시간 성장률 지표 및 발굴 타임라인 데이터
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            // 좌측: 성장률 수치 + 라벨 (Neon Green)
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = growthRate,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    color = GreenNormal,
                    letterSpacing = (-0.5).sp
                )
                Text(
                    text = "성장률",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = GreenNormal,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            // 우측: 발굴 날짜 및 경과 시간 스택 정보
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "발굴일 $discoveryDate",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Normal,
                    color = Gray400
                )
                Text(
                    text = elapsedTime,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = GreenNormal
                )
            }
        }
    }
}

@Preview(name = "Dig Info Card - Extreme Long Text Preview", showBackground = true)
@Composable
fun DigInfoCardLongTextPreview() {
    Column(
        modifier = Modifier
            .background(GrayBlack)
            .padding(16.dp)
    ) {
        DigInfoCard(
            title = "0+0", // 엄청 긴 제목
            artist = "한로로",      // 엄청 긴 아티스트명
            description = "아무것도 가진 게 없는 우리라도, 서로가 함께라면 영원을 꿈꿀 수 있다는 위로와 청춘의 찬가.",
            growthRate = "+4,723%",
            discoveryDate = "24.03.15",
            elapsedTime = "8개월 경과",
            onShareClick = {},
            onCloseClick = {}
        )
    }
}