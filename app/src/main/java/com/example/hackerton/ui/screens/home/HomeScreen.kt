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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.hackerton.R
import com.example.hackerton.data.model.DigListItem
import com.example.hackerton.data.repository.ListDigsResult
import com.example.hackerton.data.repository.SongRepository
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.AppTopBar
import com.example.hackerton.ui.components.ArtistBigBlock
import com.example.hackerton.ui.components.ArtistBigBlockEmpty
import com.example.hackerton.ui.components.ArtistSmallBlock
import com.example.hackerton.ui.components.ArtistSmallBlockEmpty
import com.example.hackerton.ui.components.BrandGradientBackground
import com.example.hackerton.ui.theme.Gray800
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenLightActive
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Heading

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onSearchSubmit: (String) -> Unit = {},
    onSongClick: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current
    val repo = remember(context) { SongRepository.get(context) }

    val fetchedPages = remember { mutableStateMapOf<Int, List<DigListItem>>() }
    var totalPages by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { maxOf(totalPages, 1) })

    // 1) Repository가 dig 성공 후 직접 알려주는 신호 — 가장 확실한 invalidate 경로
    val digsInvalidated by repo.digsInvalidated.collectAsState()
    LaunchedEffect(digsInvalidated) {
        if (digsInvalidated > 0) {
            fetchedPages.clear()
            totalPages = 0
        }
    }
    // 2) 다른 경로(앱 백그라운드 → 복귀 등)에서 resume 시 안전망
    LifecycleResumeEffect(Unit) {
        fetchedPages.clear()
        totalPages = 0
        onPauseOrDispose { }
    }

    LaunchedEffect(pagerState.currentPage, totalPages) {
        val current = pagerState.currentPage
        if (fetchedPages.containsKey(current)) return@LaunchedEffect
        when (val r = repo.listDigs(current)) {
            is ListDigsResult.Success -> {
                fetchedPages[current] = r.data.content
                if (r.data.totalPages != totalPages) totalPages = r.data.totalPages
            }
            is ListDigsResult.Error -> {
                fetchedPages[current] = emptyList()
            }
        }
    }

    BrandGradientBackground(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(Modifier.height(16.dp))

            AppTopBar(
                trailing = {
                    Image(
                        painter = painterResource(R.drawable.ic_plus),
                        contentDescription = "추가",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(24.dp)
                            .clickable(onClick = onAddClick),
                    )
                },
            )

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
                        painter = painterResource(R.drawable.ic_find),
                        contentDescription = null,
                        tint = GrayWhite,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
//                    withStyle(SpanStyle(color = GreenLightActive)) { append(nickname) }
                    withStyle(SpanStyle(color = GrayWhite)) { append("내가 발굴한 곡이예요") }
                },
                style = Heading,
            )

            Spacer(Modifier.height(32.dp))

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { pageIndex ->
                val items = fetchedPages[pageIndex].orEmpty()
                Column {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        BigSlot(
                            item = items.getOrNull(0),
                            onSongClick = onSongClick,
                            modifier = Modifier.weight(2f),
                        )
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            SmallSlot(
                                item = items.getOrNull(1),
                                onSongClick = onSongClick,
                                modifier = Modifier.fillMaxWidth(),
                            )
                            SmallSlot(
                                item = items.getOrNull(2),
                                onSongClick = onSongClick,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }

                    Spacer(Modifier.height(8.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SmallSlot(
                            item = items.getOrNull(3),
                            onSongClick = onSongClick,
                            modifier = Modifier.weight(1f),
                        )
                        SmallSlot(
                            item = items.getOrNull(4),
                            onSongClick = onSongClick,
                            modifier = Modifier.weight(1f),
                        )
                        SmallSlot(
                            item = items.getOrNull(5),
                            onSongClick = onSongClick,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
            }

            if (totalPages > 1) {
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                ) {
                    repeat(totalPages) { i ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(
                                    if (i == pagerState.currentPage) GreenLightActive else Gray800
                                )
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun BigSlot(
    item: DigListItem?,
    onSongClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (item != null) {
        ArtistBigBlock(
            name = item.title,
            painter = rememberAsyncImagePainter(item.thumbnailUrl),
            onClick = { onSongClick(item.digId.toString()) },
            modifier = modifier,
        )
    } else {
        ArtistBigBlockEmpty(modifier = modifier)
    }
}

@Composable
private fun SmallSlot(
    item: DigListItem?,
    onSongClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (item != null) {
        ArtistSmallBlock(
            name = item.title,
            painter = rememberAsyncImagePainter(item.thumbnailUrl),
            onClick = { onSongClick(item.digId.toString()) },
            modifier = modifier,
        )
    } else {
        ArtistSmallBlockEmpty(modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HackertonTheme {
        HomeScreen()
    }
}
