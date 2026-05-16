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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
import coil3.compose.SubcomposeAsyncImage
import com.example.hackerton.R
import com.example.hackerton.data.local.DiscoveredSongsStore
import com.example.hackerton.data.model.DigRequest
import com.example.hackerton.data.model.SongSearchRequest
import com.example.hackerton.data.model.SongSearchResponse
import com.example.hackerton.data.network.Api
import com.example.hackerton.util.DeviceId
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.DigButton
import com.example.hackerton.ui.components.DigChallengePopup
import com.example.hackerton.ui.theme.BodyNormal
import com.example.hackerton.ui.theme.Caption
import com.example.hackerton.ui.theme.Gray300
import com.example.hackerton.ui.theme.Gray400
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenLightActive
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Heading
import com.example.hackerton.ui.theme.LabelNormal
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Composable
fun FindScreen(
    initialQuery: String = "",
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var query by remember { mutableStateOf(initialQuery) }
    var showChallengePopup by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf<SongSearchResponse?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(query) {
        val keyword = query.trim()
        if (keyword.isBlank()) {
            result = null
            errorMessage = "검색 결과가 없습니다."
            isLoading = false
            return@LaunchedEffect
        }
        delay(400) // debounce
        isLoading = true
        errorMessage = null
        try {
            val resp = Api.service.searchSong(SongSearchRequest(keyword))
            if (resp.success) {
                result = resp.data
                errorMessage = if (resp.data == null) "검색 결과가 없습니다." else null
            } else {
                result = null
                errorMessage = resp.error?.message ?: "검색 결과가 없습니다."
            }
        } catch (e: HttpException) {
            result = null
            errorMessage = if (e.code() == 404) "검색 결과가 없습니다."
            else e.message ?: "검색 실패"
        } catch (e: Exception) {
            result = null
            errorMessage = e.message ?: "네트워크 오류"
        } finally {
            isLoading = false
        }
    }

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
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "뒤로",
                        tint = GrayWhite,
                    )
                }
                Image(
                    painter = painterResource(R.drawable.logo_hongdae),
                    contentDescription = "홍대병동",
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            Spacer(Modifier.height(20.dp))

            AppTextField(
                value = query,
                onValueChange = { query = it },
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

            Spacer(Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            CircularProgressIndicator(color = GreenLightActive)
                        }
                    }

                    result != null -> {
                        val song = result!!
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            SubcomposeAsyncImage(
                                model = song.thumbnailUrl,
                                contentDescription = "${song.title} 썸네일",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f)
                                    .clip(RoundedCornerShape(20.dp)),
                                loading = {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center,
                                    ) {
                                        CircularProgressIndicator(color = GreenLightActive)
                                    }
                                },
                                error = {
                                    Image(
                                        painter = painterResource(R.drawable.artist_big),
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize(),
                                    )
                                },
                            )

                            Spacer(Modifier.height(24.dp))

                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                            ) {
                                Text(
                                    text = song.title,
                                    style = Heading.copy(fontWeight = FontWeight.Bold),
                                    color = GrayWhite,
                                )
                                Text(
                                    text = song.artist,
                                    style = LabelNormal,
                                    color = GrayWhite,
                                )
                            }

                            Spacer(Modifier.height(8.dp))

                            Text(
                                text = formatUploadDate(song.uploadDate),
                                style = Caption,
                                color = Gray400,
                            )

                            Spacer(Modifier.height(20.dp))

                            Text(
                                text = "조회수 ${"%,d".format(song.viewCount)}회",
                                style = LabelNormal,
                                color = GrayWhite,
                            )

                            Spacer(Modifier.height(16.dp))
                        }
                    }

                    errorMessage != null -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = errorMessage!!,
                                style = BodyNormal,
                                color = Gray300,
                            )
                        }
                    }
                }
            }

            if (result != null && !isLoading) {
                DigButton(
                    text = "발굴하기",
                    onClick = { showChallengePopup = true },
                    showArrow = true,
                )
                Spacer(Modifier.height(24.dp))
            }
        }

        if (showChallengePopup) {
            val song = result
            Dialog(onDismissRequest = { showChallengePopup = false }) {
                DigChallengePopup(
                    title = song?.title.orEmpty(),
                    artist = song?.artist.orEmpty(),
                    discoveryDate = LocalDate.now().format(DISCOVERY_DATE_FORMAT),
                    currentViews = song?.let { "${"%,d".format(it.viewCount)}회" }.orEmpty(),
                    thumbnailUrl = song?.thumbnailUrl.orEmpty(),
                    onCloseClick = { showChallengePopup = false },
                    onConfirmClick = {
                        if (song != null) {
                            scope.launch {
                                val discoveredAt = LocalDateTime.now()
                                    .truncatedTo(ChronoUnit.SECONDS)
                                    .toString()
                                DiscoveredSongsStore.add(
                                    context,
                                    song.copy(discoveredAt = discoveredAt),
                                )
                                try {
                                    Api.service.registerDig(
                                        DigRequest(
                                            userId = DeviceId.userId(context),
                                            videoId = song.videoId,
                                            title = song.title,
                                            artist = song.artist,
                                            viewCount = song.viewCount,
                                            uploadDate = song.uploadDate,
                                            thumbnailUrl = song.thumbnailUrl,
                                            comment = "",
                                        )
                                    )
                                } catch (_: HttpException) {
                                    // 409(이미 등록) 포함 - 로컬 저장은 그대로 두고 진행
                                } catch (_: Exception) {
                                    // 네트워크 실패 - 로컬 저장만 유지
                                }
                                showChallengePopup = false
                                onBack()
                            }
                        } else {
                            showChallengePopup = false
                        }
                    },
                )
            }
        }
    }
}

private fun formatUploadDate(iso: String): String = runCatching {
    val parts = iso.split("-")
    "${parts[0].takeLast(2)}.${parts[1]}.${parts[2]}"
}.getOrDefault(iso)

private val DISCOVERY_DATE_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("yy.MM.dd")

@Preview(showBackground = true)
@Composable
private fun FindScreenPreview() {
    HackertonTheme {
        FindScreen()
    }
}
