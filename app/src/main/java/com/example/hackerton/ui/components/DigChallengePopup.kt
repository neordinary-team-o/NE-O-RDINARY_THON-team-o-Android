package com.example.hackerton.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.*

/**
 * [DigChallengePopup]
 * 상단 타이틀을 mining_text.xml 그래픽으로 교체한 다이얼로그 팝업
 */
@Composable
fun DigChallengePopup(
    title: String,
    artist: String,
    discoveryDate: String,
    currentViews: String,
    onCloseClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(Gray900)
            .padding(horizontal = 24.dp, vertical = 28.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // ------------------------------------------------------------------
        // [레이어 1] 상단 헤더: 그래픽 타이틀(mining_text) + 닫기 버튼(x_icon)
        // ------------------------------------------------------------------
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // 🔥 "발굴 도전!" 텍스트를 제거하고 xml 파일로 교체했습니다.
            Image(
                painter = painterResource(id = R.drawable.mining_text),
                contentDescription = "발굴 도전!",
                // 높이나 너비가 너무 크거나 작으면 이 height 값을 조절해 주세요 (예: 24.dp, 32.dp 등)
                modifier = Modifier.height(20.dp)
            )

            IconButton(
                onClick = onCloseClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .size(28.dp)
            ) {
                // 🔥 x_icon.xml 연결 및 원본 색상 유지
                Icon(
                    painter = painterResource(id = R.drawable.x_icon),
                    contentDescription = "닫기",
                    // Color.Unspecified를 주면 xml 파일에 정의된 원본 색상과 투명도가 그대로 렌더링됩니다.
                    tint = Color.Unspecified
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ------------------------------------------------------------------
        // [레이어 2] 앨범 아트 이미지
        // ------------------------------------------------------------------
        ArtistBigBlock(
            name = title, // 팝업으로 전달받은 곡 제목("강남스타일")을 그대로 넘겨줍니다.
            painter = painterResource(id = R.drawable.share_icon), // 나중에 실제 앨범 에셋으로 교체
            onClick = { /* 팝업 안에서는 이미지를 눌러도 동작이 필요 없다면 비워둡니다 */ },
            modifier = Modifier.size(120.dp) // 피그마 시안에 맞게 160dp로 고정해주면 알아서 160x160 정사각형이 됩니다.
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ------------------------------------------------------------------
        // [레이어 3] 곡 타이틀 및 아티스트
        // ------------------------------------------------------------------
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                style = Title.copy(
                    fontWeight = FontWeight.Bold, // Title 폰트에 볼드체를 덧씌움
                    color = GrayWhite             // 텍스트 색상 지정
                )
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = artist,
                style = LabelNormal.copy()
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // ------------------------------------------------------------------
        // [레이어 4] 날짜 및 조회수 정보
        // ------------------------------------------------------------------
        Text(
            text = "발굴일 $discoveryDate",
            style = Caption.copy()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "현재 조회수 $currentViews",
            style = Caption.copy()
        )

        Spacer(modifier = Modifier.height(36.dp))

        // ------------------------------------------------------------------
        // [레이어 5] 하단 액션 버튼
        // ------------------------------------------------------------------
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(100.dp))
                .background(GreenNormal)
                .clickable { onConfirmClick() },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "확인",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = GrayBlack
            )
        }
    }
}

// ==========================================================================
// 👁️ 팝업 다이얼로그 프리뷰 검증
// ==========================================================================
@Preview(name = "Dig Challenge Popup Preview", showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun DigChallengePopupPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        DigChallengePopup(
            title = "강남스타일",
            artist = "PSY",
            discoveryDate = "24.03.15",
            currentViews = "18,891회",
            onCloseClick = {},
            onConfirmClick = {}
        )
    }
}