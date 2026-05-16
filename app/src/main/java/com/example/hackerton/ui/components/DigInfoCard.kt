package com.example.hackerton.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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

/**
 * [FullDigInfoCard]
 * 네가 올린 상황별 대응 완벽판 [DigCommentInput]을 하단에 정확히 수용한 완전체 카드 컴포넌트
 */
@Composable
fun DigInfoCard(
    // 1. 대시보드 메타데이터
    title: String,
    artist: String,
    description: String,
    growthRate: String,
    discoveryDate: String,
    elapsedTime: String,

    // 2. 🔥 하단 한줄평 컴포넌트 연동을 위한 상태 및 콜백 변수들
    commentValue: String,
    onCommentValueChange: (String) -> Unit,
    isCommentSubmitted: Boolean,
    onCommentActionClick: () -> Unit,

    // 3. 컨트롤 인터랙션
    onShareClick: () -> Unit,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Gray900.copy(alpha = 0.65f)) // 반투명 글래스모피즘 톤 유지
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {
        // [레이어 1] 상단 헤더 영역
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onShareClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.share_icon),
                        contentDescription = "공유하기",
                        tint = GrayWhite
                    )
                }
                IconButton(onClick = onCloseClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.x_icon),
                        contentDescription = "닫기",
                        tint = GrayWhite
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // [레이어 2] 중단 서사형 문구
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge.copy(
                lineHeight = 24.sp,
                color = Gray200
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(28.dp))

        // [레이어 3] 하단 성장률 및 타임라인 데이터
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
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

        // ------------------------------------------------------------------
        // [레이어 4] 🔥 네가 올린 완벽한 DigCommentInput 컴포넌트 호출부
        // ------------------------------------------------------------------
        Spacer(modifier = Modifier.height(24.dp))

        DigCommentInput(
            value = commentValue,
            onValueChange = onCommentValueChange,
            isSubmitted = isCommentSubmitted,
            onActionClick = onCommentActionClick,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// ==========================================================================
// 👁️ 완전체 통합 카드 상태 테스트 Preview
// ==========================================================================
@Preview(name = "Full Dig Info Card Combined Preview", showBackground = true, widthDp = 360)
@Composable
fun DigInfoCardPreview() {
    var commentText by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(GrayBlack)
            .padding(16.dp)
    ) {
        DigInfoCard(
            title = "0+0",
            artist = "한로로",
            description = "아무것도 가진 게 없는 우리라도, 서로가 함께라면 영원을 꿈꿀 수 있다는 위로와 청춘의 찬가.",
            growthRate = "+4,723%",
            discoveryDate = "24.03.15",
            elapsedTime = "8개월 경과",

            commentValue = commentText,
            onCommentValueChange = {
                commentText = it
                if (isSubmitted) isSubmitted = false // 수정 시작 시 자동으로 화살표 상태로 리턴
            },
            isCommentSubmitted = isSubmitted,
            onCommentActionClick = {
                if (commentText.isNotEmpty()) {
                    isSubmitted = !isSubmitted // 등록 상태 토글 인터랙션
                }
            },
            onShareClick = {},
            onCloseClick = {}
        )
    }
}