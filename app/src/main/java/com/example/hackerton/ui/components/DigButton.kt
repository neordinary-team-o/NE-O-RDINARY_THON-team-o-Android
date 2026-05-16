package com.example.hackerton.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class DigButtonVariant(val backgroundColor: Color, val contentColor: Color) {
    BrightGreen(backgroundColor = Color(0xFF82F282), contentColor = Color(0xFF121212)),
    DeepGreen(backgroundColor = Color(0xFF5EC45E), contentColor = Color(0xFF121212)),
    White(backgroundColor = Color(0xFFFFFFFF), contentColor = Color(0xFF121212)),
    Gray(backgroundColor = Color(0xFFDCDCE4), contentColor = Color(0xFF757575))
}

@Composable
fun DigButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    variant: DigButtonVariant = DigButtonVariant.BrightGreen,
    enabled: Boolean = true,
    showArrow: Boolean = false
) {
    // Gray 변형이거나 enabled가 false일 때의 컬러 분기 처리
    val currentVariant = if (!enabled) DigButtonVariant.Gray else variant

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp), // 터치 영역 및 시각적 임팩트를 위한 표준 높이
        enabled = enabled,
        shape = CircleShape, // 이미지의 완전한 라운드 캡슐 형태 구현
        colors = ButtonDefaults.buttonColors(
            containerColor = currentVariant.backgroundColor,
            contentColor = currentVariant.contentColor,
            disabledContainerColor = DigButtonVariant.Gray.backgroundColor,
            disabledContentColor = DigButtonVariant.Gray.contentColor
        )
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.3).sp
            )

            if (showArrow) {
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "Move Next",
                    modifier = Modifier.height(18.dp).width(18.dp)
                )
            }
        }
    }
}