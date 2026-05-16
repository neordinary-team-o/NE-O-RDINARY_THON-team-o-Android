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
import coil3.compose.AsyncImage
import com.example.hackerton.R
import com.example.hackerton.ui.theme.*

// FindScreen.kt의 context를 참조하여 작성된 팝업 컴포저블
@Composable
fun DigChallengePopup(
    title: String,
    artist: String,
    discoveryDate: String,
    currentViews: String,
    thumbnailUrl: String,
    onCloseClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 테마에 따라 다를 수 있지만, 이미지에 기반하여 짙은 회색 배경 정의
    // 만약 테마에 정의되어 있다면 GrayBlack 등을 사용하세요.
    val popupBackgroundColor = GrayBlack.copy(alpha = 0.98f) // 또는 테마 색상 사용 예: Color(0xFF1C1C1E)

    // 팝업 카드의 전체 컨테이너
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(32.dp)) // 둥근 모서리
            .background(popupBackgroundColor) // 다크 테마 배경
            .padding(horizontal = 24.dp, vertical = 28.dp), // 내부 패딩
        horizontalAlignment = Alignment.CenterHorizontally // 중앙 정렬
    ) {
        // [레이어 1] 헤더 영역 (제목 + 닫기 버튼)
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.mining_text),
                contentDescription = "발굴 도전!",
                contentScale = ContentScale.Fit, // 비율 깨짐 방지
                modifier = Modifier
                    .align(Alignment.Center) // 🔥 기존 Text처럼 Box 안에서 정중앙 정렬 유지
                    .height(20.dp)           // 피그마 시안에 맞춰 높이 조절 (필요 시 24.dp 등으로 변경)
                    .wrapContentWidth()
            )

            IconButton(
                onClick = onCloseClick,
                modifier = Modifier
                    .align(Alignment.CenterEnd) // 닫기 버튼 우측 배치
                    .size(24.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.x_icon), // 닫기 아이콘 리소스 연결
                    contentDescription = "닫기",
                    tint = GrayWhite // 아이콘 색상
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp)) // 제목과 앨범 아트 사이 간격

        // [레이어 2] 앨범 아트 이미지 — API thumbnailUrl 로딩
        AsyncImage(
            model = thumbnailUrl,
            contentDescription = "Album Art",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.artist_big),
            error = painterResource(id = R.drawable.artist_big),
            fallback = painterResource(id = R.drawable.artist_big),
            modifier = Modifier
                .size(160.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
        )

        Spacer(modifier = Modifier.height(24.dp)) // 앨범 아트와 곡명 사이 간격

        // [레이어 3] 곡 타이틀 및 아티스트
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
            // Arrangement.spacedBy(8.dp)는 FindScreen에서 Row 내부에 사용되지 않아 개별 Spacer 사용
        ) {
            Text(
                text = title, // "강남스타일"
                // FindScreen의 Heading 스타일 참조
                style = Heading.copy(fontWeight = FontWeight.Bold),
                color = GrayWhite
            )
            Spacer(modifier = Modifier.width(8.dp)) // 타이틀과 아티스트 사이 간격
            Text(
                text = artist, // "PSY"
                // FindScreen의 LabelNormal 스타일 참조
                style = LabelNormal,
                color = Gray400 // 약간 연한 회색
            )
        }

        Spacer(modifier = Modifier.height(12.dp)) // 곡명과 상세 정보 사이 간격

        // [레이어 4] 상세 정보 영역 (발굴일, 현재 조회수)
        // FindScreen의 Caption 및 LabelNormal 스타일 참조
        Text(
            text = "발굴일 $discoveryDate", // "발굴일 24.03.15"
            style = Caption, // 캡션 스타일
            color = Gray400
        )

        Spacer(modifier = Modifier.height(16.dp)) // 발굴일과 조회수 사이 간격

        Text(
            text = "현재 조회수 $currentViews", // "현재 조회수 18,891회"
            style = LabelNormal, // 라벨 일반 스타일
            color = GrayWhite
        )

        Spacer(modifier = Modifier.height(36.dp)) // 조회수와 버튼 사이 넓은 간격

        // [레이어 5] 하단 확인 버튼
        // DigButton 대신 확인 전용 초록색 버튼으로 구현
        Box(
            modifier = Modifier
                .fillMaxWidth() // 너비 꽉 채우기
                .height(60.dp) // 버튼 높이 지정
                .clip(RoundedCornerShape(100.dp)) // 알약 모양 모서리
                .background(GreenNormal) // 초록색 배경
                .clickable { onConfirmClick() }, // 클릭 이벤트 처리
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "확인",
                style = LabelNormal.copy(fontWeight = FontWeight.Bold),
                color = GrayBlack // 텍스트 색상
            )
        }
    }
}

// ==========================================================================
// [ Cite: image_0.png ] 기반 팝업 다이얼로그 프리뷰
// ==========================================================================
@Preview(name = "Dig Challenge Popup Preview", showBackground = true, backgroundColor = 0xFF121212)
@Composable
fun DigChallengePopupPreview() {
    HackertonTheme {
        // 프리뷰에서는 다이얼로그 바깥 영역을 시뮬레이션하기 위해 패딩 적용
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayBlack) // 배경 어둡게 처리
                .padding(32.dp),
            contentAlignment = Alignment.Center // 중앙 배치
        ) {
            DigChallengePopup(
                title = "강남스타일",
                artist = "PSY",
                discoveryDate = "24.03.15",
                currentViews = "18,891회",
                thumbnailUrl = "",
                onCloseClick = { /* 닫기 클릭 */ },
                onConfirmClick = { /* 확인 클릭 */ }
            )
        }
    }
}

