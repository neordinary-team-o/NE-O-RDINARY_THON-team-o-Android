package com.example.hackerton.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.ArtistBigBlock
import com.example.hackerton.ui.components.ArtistSmallBlock
import com.example.hackerton.ui.theme.Gray800
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenLightActive
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Heading

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchSubmit: (String) -> Unit = {},
    onSongClick: (String) -> Unit = {},
) {
    val nickname = "홍대병동"
    val dummyPainter = painterResource(R.drawable.arrow_icon)
    var searchQuery by rememberSaveable { mutableStateOf("") }

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
                .verticalScroll(rememberScrollState())
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

            // Search — IME 검색 키 누르면 FindScreen으로 이동
            AppTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "검색어를 입력하세요",
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchQuery.isNotBlank()) {
                            onSearchSubmit(searchQuery)
                            searchQuery = ""
                        }
                    },
                ),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.find_icon),
                        contentDescription = null,
                        tint = GrayWhite,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = GreenLightActive)) { append(nickname) }
                    withStyle(SpanStyle(color = GrayWhite)) { append("님이 발굴한 곡이예요") }
                },
                style = Heading,
            )

            Spacer(Modifier.height(32.dp))

            // Row 1: big left + 2 small stacked right
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ArtistBigBlock(
                    name = "0+0",
                    painter = painterResource(R.drawable.artist_big),
                    onClick = { onSongClick("0+0") },
                    modifier = Modifier.weight(2f),
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ArtistSmallBlock(
                        name = "끝나지...",
                        painter = dummyPainter,
                        onClick = { onSongClick("끝나지...") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    ArtistSmallBlock(
                        name = "폰서트",
                        painter = dummyPainter,
                        onClick = { onSongClick("폰서트") },
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Row 2: 2 small + add button
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ArtistSmallBlock(
                    name = "멸종",
                    painter = dummyPainter,
                    onClick = { onSongClick("멸종") },
                    modifier = Modifier.weight(1f),
                )
                ArtistSmallBlock(
                    name = "daisy.",
                    painter = dummyPainter,
                    onClick = { onSongClick("daisy.") },
                    modifier = Modifier.weight(1f),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Gray800)
                        .clickable { },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painterResource(R.drawable.add_icon),
                        contentDescription = "추가",
                        tint = GrayWhite,
                        modifier = Modifier.size(32.dp),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Row 3: 3 empty slots
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Gray800))
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Gray800))
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Gray800))
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HackertonTheme {
        HomeScreen()
    }
}
