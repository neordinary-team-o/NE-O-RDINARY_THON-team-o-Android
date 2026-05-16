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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackerton.R
import com.example.hackerton.ui.components.AppTopBar
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
    snapshotViewCount: Long?,
    currentViewCount: Long?,
    growthRate: Double?,
    achievementBadge: String?,
    narrativeMessage: String?,
    painter: Painter,
    onBack: () -> Unit,
    isLoading: Boolean = false,
) {
    val snapshotLabel = snapshotViewCount?.let { "${"%,d".format(it)}회" } ?: "-"
    val currentLabel = currentViewCount?.let { "${"%,d".format(it)}회" } ?: "-"
    val growthLabel = growthRate?.let { "${if (it >= 0) "+" else ""}${"%.3f".format(it)}%" } ?: "-"
    val badgeLabel: String = formatBadge(achievementBadge)
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.img_share_background),
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
            AppTopBar(
                logoRes = R.drawable.logo_dig_success,
                logoContentDescription = "발굴 성공!",
                onBack = onBack,
            )

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(color = GreenNormal)
                }
                return@Column
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

            // 제목에 '(' 가 있으면 그 앞까지를 메인 제목으로 쓰고, '('부터는 아래 줄에 작게 보여줌.
            // 예: "마지막처럼 (Last Time)" -> "마지막처럼 한로로" / "(Last Time)"
            val parenIdx = songTitle.indexOf('(')
            val titleMain = if (parenIdx > 0) songTitle.substring(0, parenIdx).trimEnd() else songTitle
            val titleSub = if (parenIdx > 0) songTitle.substring(parenIdx) else null

            Column(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = titleMain,
                        style = Title.copy(fontWeight = FontWeight.Bold, color = GrayWhite),
                    )
                    Text(
                        text = artist,
                        style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = Gray300),
                    )
                }
                if (titleSub != null) {
                    Text(
                        text = titleSub,
                        style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = Gray300),
                    )
                }
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
                    badgeLabel,
                    style = LabelNormal.copy(fontWeight = FontWeight.SemiBold, color = GreenNormal),
                )
            }

            Spacer(Modifier.weight(1f))

            Text(
                text = highlightNarrative(narrativeMessage.orEmpty()),
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
                        Text(snapshotLabel, style = Caption.copy(color = Gray300))
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text("현재 조회수", style = Caption.copy(color = GrayWhite))
                        Text(currentLabel, style = Caption.copy(color = GrayWhite))
                    }
                }
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = growthLabel,
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

/**
 * "5,800만명", "8개월" 같은 숫자+단위 토큰을 GreenNormal로 강조.
 * 주의: "12,400명"(snapshot)은 강조하지 않는다 — Figma 사양상 "만명"/"개월"만 대상.
 */
private val NARRATIVE_HIGHLIGHT_REGEX =
    Regex("""\d[\d,]*(?:만명|만|억명|개월|년)""")

private fun highlightNarrative(text: String): AnnotatedString = buildAnnotatedString {
    if (text.isEmpty()) return@buildAnnotatedString
    var cursor = 0
    NARRATIVE_HIGHLIGHT_REGEX.findAll(text).forEach { m ->
        if (m.range.first > cursor) append(text.substring(cursor, m.range.first))
        withStyle(SpanStyle(color = GreenNormal)) { append(m.value) }
        cursor = m.range.last + 1
    }
    if (cursor < text.length) append(text.substring(cursor))
}

/**
 * achievementName 매핑.
 * 백엔드가 null/빈 값을 보내면 기본 "Trend Setter!"로 표기 (디자인상 항상 노출).
 */
private fun formatBadge(badge: String?): String = when (badge) {
    null, "" -> "Trend Setter!"
    "TRENDSETTER", "TREND_SETTER" -> "Trend Setter!"
    "TREND_CATCHER" -> "Trend Catcher!"
    else -> badge
}

@Preview(showBackground = true)
@Composable
private fun ShareScreenPreview() {
    ShareScreen(
        songTitle = "0+0",
        artist = "한로로",
        discoveryDate = "24.03.15",
        elapsedTime = "8개월",
        snapshotViewCount = 14205,
        currentViewCount = 58_000_000,
        growthRate = 4.723,
        achievementBadge = "TREND_CATCHER",
        narrativeMessage = "당신이 14,205명이 듣던 시절 발견한 음악이 지금 58,000,000명에게 재생되고 있습니다. 당신의 귀는 시대보다 8개월 빨랐습니다.",
        painter = painterResource(R.drawable.img_artist_placeholder),
        onBack = {},
    )
}
