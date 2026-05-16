package com.example.hackerton.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.GrayWhite

private val AppTopBarHeight = 48.dp

/**
 * 공통 상단 바 - 좌측 뒤로 가기(optional), 중앙 로고, 우측 trailing 슬롯(optional).
 */
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    @DrawableRes logoRes: Int = R.drawable.logo_hongdae,
    logoContentDescription: String? = "홍대병동",
    onBack: (() -> Unit)? = null,
    trailing: @Composable (BoxScope.() -> Unit)? = null,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTopBarHeight),
    ) {
        if (onBack != null) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart),
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_back),
                    contentDescription = "뒤로",
                    tint = GrayWhite,
                    modifier = Modifier.size(28.dp),
                )
            }
        }
        Image(
            painter = painterResource(id = logoRes),
            contentDescription = logoContentDescription,
            modifier = Modifier.align(Alignment.Center),
        )
        trailing?.invoke(this)
    }
}
