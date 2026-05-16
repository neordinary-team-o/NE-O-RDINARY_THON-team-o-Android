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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.hackerton.R
import com.example.hackerton.data.model.DigListItem
import com.example.hackerton.data.repository.ListDigsResult
import com.example.hackerton.data.repository.SearchMyDigsResult
import com.example.hackerton.data.repository.SongRepository
import kotlinx.coroutines.delay
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
    onSongClick: (String) -> Unit = {},
    onAddClick: () -> Unit = {},
) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val isSearching = searchQuery.isNotBlank()

    val context = LocalContext.current
    val repo = remember(context) { SongRepository.get(context) }

    // ── 일반 모드: 페이지네이션된 dig 목록 ──
    val fetchedPages = remember { mutableStateMapOf<Int, List<DigListItem>>() }
    var totalPages by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(pageCount = { maxOf(totalPages, 1) })

    // ── 검색 모드: keyword → 결과 ──
    var searchResults by remember { mutableStateOf<List<DigListItem>>(emptyList()) }

    // dig 성공 후 Repository가 발화하는 invalidation 신호.
    // 캐시 비우고 page 0으로 스크롤해서 stale pagerState 문제(이전 페이지 인덱스 잔류)를 막는다.
    val digsInvalidated by repo.digsInvalidated.collectAsState()
    LaunchedEffect(digsInvalidated) {
        if (digsInvalidated > 0) {
            Log.d("HomeScreen", "invalidated -> clear cache + scroll to page 0")
            fetchedPages.clear()
            totalPages = 0
            pagerState.scrollToPage(0)
        }
    }

    // 일반 모드에서만 페이지별 fetch (검색 중이면 페이저는 비활성)
    LaunchedEffect(pagerState.currentPage, totalPages, isSearching) {
        if (isSearching) return@LaunchedEffect
        val current = pagerState.currentPage
        if (fetchedPages.containsKey(current)) return@LaunchedEffect
        when (val r = repo.listDigs(current)) {
            is ListDigsResult.Success -> {
                Log.d("HomeScreen", "fetch success page=$current items=${r.data.content.size} totalPages=${r.data.totalPages}")
                fetchedPages[current] = r.data.content
                if (r.data.totalPages != totalPages) totalPages = r.data.totalPages
            }
            is ListDigsResult.Error -> {
                Log.w("HomeScreen", "fetch error page=$current msg=${r.message}")
                fetchedPages[current] = emptyList()
            }
        }
    }

    // 검색어 변경 시 디바운스 후 /api/digs/me/search 호출
    LaunchedEffect(searchQuery) {
        if (!isSearching) {
            searchResults = emptyList()
            return@LaunchedEffect
        }
        delay(250)
        when (val r = repo.searchMyDigs(searchQuery)) {
            is SearchMyDigsResult.Success -> searchResults = r.items
            is SearchMyDigsResult.Error -> {
                Log.w("HomeScreen", "search error ${r.message}")
                searchResults = emptyList()
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

            // Search — 입력 즉시 /api/digs/me/search 호출 (디바운스).
            // 검색 결과가 있으면 그리드 큰 슬롯부터 채워서 보여준다.
            AppTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = "검색어를 입력하세요",
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_find),
                        contentDescription = null,
                        tint = GrayWhite,
                        modifier = Modifier.size(20.dp),
                    )
                },
                trailingIcon = if (isSearching) {
                    {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "지우기",
                            tint = GrayWhite,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable { searchQuery = "" },
                        )
                    }
                } else null,
            )

            Spacer(Modifier.height(32.dp))

            Text(
                text = buildAnnotatedString {
//                    withStyle(SpanStyle(color = GreenLightActive)) { append(nickname) }
                    withStyle(SpanStyle(color = GrayWhite)) { append("내가 발굴한 곡이예요") }
                },
                style = Heading,
            )

            Spacer(Modifier.height(16.dp))

            if (isSearching) {
                // 검색 모드: 1페이지로 결과 표시 (큰 슬롯부터 순서대로 채움)
                DigGrid(
                    items = searchResults,
                    onSongClick = onSongClick,
                )
            } else {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxWidth(),
                ) { pageIndex ->
                    DigGrid(
                        items = fetchedPages[pageIndex].orEmpty(),
                        onSongClick = onSongClick,
                    )
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
            }
            Spacer(Modifier.height(8.dp))
        }
    }
}

@Composable
private fun DigGrid(
    items: List<DigListItem>,
    onSongClick: (String) -> Unit,
) {
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
