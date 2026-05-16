package com.example.hackerton.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.ArtistBigBlock
import com.example.hackerton.ui.components.ArtistSmallBlock
import com.example.hackerton.ui.components.DigInfoCard
import com.example.hackerton.ui.components.ShareModal
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.Gray800
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenLightActive
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Heading

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(),
) {
    val nickname = "홍대병동"
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val dummyPainter = painterResource(R.drawable.arrow_icon)

    val selectedSong by viewModel.selectedSong.collectAsStateWithLifecycle()
    var lastShownSong by remember { mutableStateOf("") }
    var showShare by remember { mutableStateOf(false) }
    LaunchedEffect(selectedSong) {
        selectedSong?.let { lastShownSong = it }
    }
    fun overlayFor(name: String): Color =
        if (selectedSong == name) Color.Black.copy(alpha = 0.1f)
        else Color.Black.copy(alpha = 0.3f)
    fun blockDim(name: String): Modifier =
        if (selectedSong != null && selectedSong != name) Modifier.drawWithContent {
            drawContent()
            drawRect(Color.Black.copy(alpha = 0.55f))
        } else Modifier
    val ambientDim: Modifier =
        if (selectedSong != null) Modifier.alpha(0.35f) else Modifier
    val tintDim: Modifier =
        if (selectedSong != null) Modifier.drawWithContent {
            drawContent()
            drawRect(Color.Black.copy(alpha = 0.55f))
        } else Modifier

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack)
            .statusBarsPadding(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(16.dp))

            // Header
            Box(modifier = Modifier.fillMaxWidth().then(ambientDim)) {
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

            // Search
            AppTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "검색어를 입력하세요",
                modifier = Modifier.fillMaxWidth().then(ambientDim),
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

            // Section label
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = GreenLightActive)) {
                        append(nickname)
                    }
                    withStyle(SpanStyle(color = GrayWhite)) {
                        append("님이 발굴한 곡이예요")
                    }
                },
                style = Heading,
                modifier = ambientDim,
            )

            Spacer(Modifier.height(32.dp))

            // Row 1: big left + 2 small stacked right
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ArtistBigBlock(
                    name = "0+0",
                    painter = painterResource(R.drawable.artist_big),
                    onClick = { viewModel.selectSong("0+0") },
                    modifier = Modifier.weight(2f).then(blockDim("0+0")),
                    tintOverlayColor = overlayFor("0+0"),
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    ArtistSmallBlock(
                        name = "끝나지...",
                        painter = dummyPainter,
                        onClick = { viewModel.selectSong("끝나지...") },
                        modifier = Modifier.fillMaxWidth().then(blockDim("끝나지...")),
                        tintOverlayColor = overlayFor("끝나지..."),
                    )
                    ArtistSmallBlock(
                        name = "폰서트",
                        painter = dummyPainter,
                        onClick = { viewModel.selectSong("폰서트") },
                        modifier = Modifier.fillMaxWidth().then(blockDim("폰서트")),
                        tintOverlayColor = overlayFor("폰서트"),
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            // Row 2: 2 small + add button
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ArtistSmallBlock(
                    name = "멸종",
                    painter = dummyPainter,
                    onClick = { viewModel.selectSong("멸종") },
                    modifier = Modifier.weight(1f).then(blockDim("멸종")),
                    tintOverlayColor = overlayFor("멸종"),
                )
                ArtistSmallBlock(
                    name = "daisy.",
                    painter = dummyPainter,
                    onClick = { viewModel.selectSong("daisy.") },
                    modifier = Modifier.weight(1f).then(blockDim("daisy.")),
                    tintOverlayColor = overlayFor("daisy."),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Gray800)
                        .clickable { }
                        .then(tintDim),
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
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Gray800).then(tintDim))
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Gray800).then(tintDim))
                Box(modifier = Modifier.weight(1f).aspectRatio(1f).clip(RoundedCornerShape(16.dp)).background(Gray800).then(tintDim))
            }

            Spacer(Modifier.height(24.dp))
        }

        AnimatedVisibility(
            visible = selectedSong != null,
            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp, vertical = 24.dp)
                .navigationBarsPadding(),
        ) {
            DigInfoCard(
                title = lastShownSong,
                artist = "한로로",
                description = "아무것도 가진 게 없는 우리라도, 서로가 함께라면 영원을 꿈꿀 수 있다는 위로와 청춘의 찬가.",
                growthRate = "+4,723%",
                discoveryDate = "24.03.15",
                elapsedTime = "8개월 경과",
                commentValue = "",
                onCommentValueChange = {},
                isCommentSubmitted = false,
                onCommentActionClick = {},
                onShareClick = { showShare = true },
                onCloseClick = { viewModel.clearSelection() },
            )
        }

        if (showShare) {
            ShareModal(
                songTitle = lastShownSong,
                artist = "한로로",
                discoveryDate = "24.03.15",
                elapsedTime = "8개월",
                growthRate = "+4,723%",
                painter = painterResource(R.drawable.artist_big),
                onShareClick = { showShare = false },
                onDismiss = { showShare = false },
            )
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
