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
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hackerton.R
import com.example.hackerton.ui.theme.*

enum class DigButtonVariant(val backgroundColor: Color, val contentColor: Color) {
    // 디자이너님이 정의해주신 토큰 변수로 색상 재지정
    BrightGreen(backgroundColor = GreenNormal, contentColor = GrayBlack),
    DeepGreen(backgroundColor = GreenDark, contentColor = GrayBlack),
    White(backgroundColor = GrayWhite, contentColor = GrayBlack),
    Gray(backgroundColor = Gray200, contentColor = Gray600)
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
    // 버튼이 비활성화(enabled = false)되면 자동으로 Gray 토큰 스타일로 전환
    val currentVariant = if (!enabled) DigButtonVariant.Gray else variant

    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        enabled = enabled,
        shape = CircleShape,
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
                    // 🔥 painterResource를 사용하여 drawable 폴더의 arrow_icon을 로드합니다.
                    painter = painterResource(id = R.drawable.arrow_icon),
                    contentDescription = "Move Next",
                    // 디자이너님이 주신 아이콘 자체 색상을 입히기 위해 tint는 Color.Unspecified로 주거나 생략할 수 있습니다.
                    // 만약 버튼 글자색(contentColor)과 강제로 맞추고 싶다면 tint = currentVariant.contentColor 를 주면 됩니다.
                    tint = Color.Unspecified,
                    modifier = Modifier.height(18.dp).width(18.dp)
                )
            }
        }
    }
}

    @Preview(name = "DigButton Variants Preview", showBackground = true)
    @Composable
    fun DigButtonGridPreview() {
        // 하드코딩된 Color(0xFF121212) 대신 디자이너님의 GrayBlack 토큰으로 배경 재지정
        Column(
            modifier = Modifier
                .background(GrayBlack)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // 1행: BrightGreen (좌/우)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.BrightGreen
                )
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.BrightGreen,
                    showArrow = true
                )
            }

            // 2행: DeepGreen (좌/우)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.DeepGreen
                )
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.DeepGreen,
                    showArrow = true
                )
            }

            // 3행: White (좌/우)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.White
                )
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.White,
                    showArrow = true
                )
            }

            // 4행: Gray / Disabled (좌/우)
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.Gray
                )
                DigButton(
                    text = "다음으로",
                    onClick = {},
                    modifier = Modifier.weight(1f),
                    variant = DigButtonVariant.Gray,
                    showArrow = true
                )
            }
        }
    }
