package com.example.hackerton.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.Gray200
import com.example.hackerton.ui.theme.Gray300
import com.example.hackerton.ui.theme.Gray600
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenDark
import com.example.hackerton.ui.theme.GreenDarkHover
import com.example.hackerton.ui.theme.GreenNormal
import com.example.hackerton.ui.theme.GreenNormalActive
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Headline

enum class CtaVariant(
    val background: Color,
    val backgroundPressed: Color,
    val contentColor: Color,
) {
    BrightGreen(GreenNormal, GreenNormalActive, GrayBlack),
    DeepGreen(GreenDark, GreenDarkHover, GrayBlack),
    White(GrayWhite, Gray200, GrayBlack),
    Gray(Gray200, Gray300, Gray600),
}

@Composable
fun CtaButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth(),
    variant: CtaVariant = CtaVariant.BrightGreen,
    hasArrow: Boolean = false,
    enabled: Boolean = true,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val bg = if (enabled) {
        if (isPressed) variant.backgroundPressed else variant.background
    } else {
        CtaVariant.Gray.background
    }
    val contentColor = if (enabled) variant.contentColor else CtaVariant.Gray.contentColor

    Row(
        modifier = modifier
            .clip(CircleShape)
            .background(bg)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick,
            )
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = Headline.copy(
                fontWeight = FontWeight.SemiBold,
                letterSpacing = (-0.02).em,
            ),
            color = contentColor,
        )
        if (hasArrow) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = contentColor,
                modifier = Modifier.size(20.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1B1F)
@Composable
private fun CtaButtonPreview() {
    HackertonTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            CtaButton(text = "발굴 시작하기", onClick = {}, variant = CtaVariant.BrightGreen)
            CtaButton(text = "발굴 시작하기", onClick = {}, variant = CtaVariant.DeepGreen)
            CtaButton(text = "건너뛰기", onClick = {}, variant = CtaVariant.White)
            CtaButton(text = "건너뛰기", onClick = {}, variant = CtaVariant.Gray)
            CtaButton(text = "로그인", onClick = {}, variant = CtaVariant.BrightGreen, hasArrow = true)
            CtaButton(text = "비활성화", onClick = {}, variant = CtaVariant.BrightGreen, enabled = false)
        }
    }
}
