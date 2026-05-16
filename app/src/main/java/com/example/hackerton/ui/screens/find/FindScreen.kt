package com.example.hackerton.ui.screens.find

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.hackerton.R
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.DigButton
import com.example.hackerton.ui.components.DigChallengePopup
import com.example.hackerton.ui.theme.Caption
import com.example.hackerton.ui.theme.Gray400
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Heading
import com.example.hackerton.ui.theme.LabelNormal

@Composable
fun FindScreen(
    initialQuery: String = "",
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf(initialQuery) }
    var showChallengePopup by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f)
                .align(Alignment.TopCenter)
                .drawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(
                                Color(0xFF03FF67).copy(alpha = 0.15f),
                                Color(0x0003FF67),
                            ),
                            center = Offset(x = size.width / 2f, y = 0f),
                            radius = maxOf(size.width, size.height),
                        )
                    )
                }
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(16.dp))

            // Header
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(R.drawable.logo_hongdae),
                    contentDescription = "홍대병동",
                    modifier = Modifier.align(Alignment.Center),
                )
                Icon(
                    painter = painterResource(R.drawable.profile_icon),
                    contentDescription = "프로필",
                    tint = GrayWhite,
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .size(32.dp),
                )
            }

            Spacer(Modifier.height(20.dp))

            // Search bar — 다 지우면 Home으로 복귀
            AppTextField(
                value = query,
                onValueChange = {
                    query = it
                    if (it.isBlank()) onBack()
                },
                placeholder = "검색어를 입력하세요",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.find_icon),
                        contentDescription = null,
                        tint = GrayWhite,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )

            if (query.isNotBlank()) {
                Spacer(Modifier.height(32.dp))

                // Album art
                Image(
                    painter = painterResource(R.drawable.artist_big),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(20.dp)),
                )

                Spacer(Modifier.height(24.dp))

                // Title + artist
                Row(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "강남스타일",
                        style = Heading.copy(fontWeight = FontWeight.Bold),
                        color = GrayWhite,
                    )
                    Text(
                        text = "PSY",
                        style = LabelNormal,
                        color = GrayWhite,
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "12.07.15",
                    style = Caption,
                    color = Gray400,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Spacer(Modifier.height(20.dp))

                Text(
                    text = "조회수 18,891회",
                    style = LabelNormal,
                    color = GrayWhite,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                )

                Spacer(Modifier.weight(1f))

                DigButton(
                    text = "발굴하기",
                    onClick = { showChallengePopup = true },
                    showArrow = true,
                )

                Spacer(Modifier.height(24.dp))
            }
        }

        if (showChallengePopup) {
            Dialog(onDismissRequest = { showChallengePopup = false }) {
                DigChallengePopup(
                    title = "강남스타일",
                    artist = "PSY",
                    discoveryDate = "24.03.15",
                    currentViews = "18,891회",
                    onCloseClick = { showChallengePopup = false },
                    onConfirmClick = { showChallengePopup = false },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FindScreenPreview() {
    HackertonTheme {
        FindScreen()
    }
}
