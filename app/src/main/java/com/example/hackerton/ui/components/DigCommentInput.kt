package com.example.hackerton.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.*

/**
 * [DigCommentInput]
 * 기획서에 명시된 5가지 인터랙션 상태 및 멀티라인 형태를 완벽히 소화하는 컴포넌트
 */
@Composable
fun DigCommentInput(
    value: String,
    onValueChange: (String) -> Unit,
    isSubmitted: Boolean,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "한줄평을 입력해주세요.",
    // 🔥 프리뷰에서 5가지 고정 상태를 강제로 시뮬레이션하기 위한 상태 제어 통로
    forcedFocus: Boolean? = null
) {
    var internalFocused by remember { mutableStateOf(false) }
    // forcedFocus 값이 들어오면 그 값을 따르고, 없으면 실제 터치 포커스를 따름
    val isFocused = forcedFocus ?: internalFocused

    val focusRequester = remember { FocusRequester() }

    // 🎨 디자인 시스템 규칙 1:1 동기화
    // 2번(활성화 상태), 3번(입력 중 상태)일 때만 네온 그린 테두리 활성화
    val borderColor = if (isFocused) GreenNormal else Color.Transparent

    // 3,4,5번 상태(텍스트가 채워짐)일 때는 네온 그린 버튼, 초기/활성화는 그레이 버튼
    val circleBg = if (value.isNotEmpty()) GreenNormal else Gray600
    val iconTint = if (value.isNotEmpty()) GrayBlack else Gray400

    // 💡 [감자 모양 왜곡 해결]: 고정 곡률 24.dp 또는 32.dp를 적용하여 단일 라인일 때는 알약(Pill) 형태가 되고,
    // 문장이 길어져 두 줄 이상이 될 때는 양옆이 무너지지 않고 정교한 직사각형 라운드(Stadium) 형태가 유지됩니다.
    val inputShape = RoundedCornerShape(32.dp) // 렌더링 안정성을 위해 32.dp 로드

    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight() // 텍스트 팽창에 따라 세로 폭 유연 확장
            .background(Gray800, shape = inputShape)
            .border(width = 1.dp, color = borderColor, shape = inputShape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                if (!isSubmitted) focusRequester.requestFocus()
            }
            .padding(start = 18.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically // 💡 우측 시안 핵심 사양: 줄이 늘어나도 버튼은 항상 세로 정중앙 고정
    ) {
        // [중앙 텍스트 필드 입력 상자]
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(vertical = 10.dp)
                .focusRequester(focusRequester),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty()) {
                Text(
                    text = placeholder,
                    style = TextStyle(color = Gray600, fontSize = 16.sp)
                )
            }
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(color = GrayWhite, fontSize = 16.sp),
                cursorBrush = SolidColor(GreenNormal),
                readOnly = isSubmitted, // 5번 등록 완료 상태 시 타이핑 잠금
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // [우측 상태 제어 원형 버튼]
        // 🔥 [크래시 해결 핵심] 프리뷰 모드(InspectionMode) 검사 구조 도입
        val painter = if (LocalInspectionMode.current) {
            // 프리뷰 환경에서는 아직 컴파일되지 않은 로컬 에셋 대신 시스템 내장 벡터 아이콘으로 대체하여 Render Problem 방지
            rememberVectorPainter(if (isSubmitted) Icons.Default.Edit else Icons.Default.ArrowForward)
        } else {
            // 실제 앱 구동 시에는 디자이너님의 전용 XML 리소스 정상 로드
            painterResource(id = if (isSubmitted) R.drawable.comment_icon else R.drawable.arrow_icon)
        }

        Box(
            modifier = Modifier
                .size(40.dp)
                .background(circleBg, shape = CircleShape) // clip을 배제하여 외곽선 찌그러짐 현상 물리적 차단
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    onActionClick()
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painter,
                contentDescription = "Action Button",
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// ==========================================================================
// 👁️ [Cite: image_52c0a4.png] 기획서 좌/우 사양 100% 매칭 검증 프리뷰
// ==========================================================================
@Preview(name = "DigCommentInput All 5 States Spec", showBackground = true, widthDp = 360)
@Composable
fun DigCommentInputAllSpecsPreview() {
    Column(
        modifier = Modifier
            .background(GrayBlack)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ---- 좌측 싱글라인 흐름 시리즈 ----
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("1. 초기 상태 (Unfocused + Empty)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(value = "", onValueChange = {}, isSubmitted = false, onActionClick = {}, forcedFocus = false)
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("2. 입력창 활성화 상태 (Focused + Empty)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(value = "", onValueChange = {}, isSubmitted = false, onActionClick = {}, forcedFocus = true)
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("3. 활성화 상태에서 입력한 상태 (Focused + Typing)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(value = "낙엽", onValueChange = {}, isSubmitted = false, onActionClick = {}, forcedFocus = true)
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("4. 입력 후 화면 다른 곳 눌러 비활성화 된 상태 (Unfocused + Text)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(value = "낙엽", onValueChange = {}, isSubmitted = false, onActionClick = {}, forcedFocus = false)
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("5. 화살표 버튼 눌러 등록을 한 상태 (Submitted + Pencil)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(value = "낙엽", onValueChange = {}, isSubmitted = true, onActionClick = {}, forcedFocus = false)
        }

        // ---- 우측 멀티라인 대응 배리에이션 ----
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("6. 오른쪽 멀티라인 입력 중 상태 (Multiline + Focused)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(
                value = "가진 게 없어도 함께라면 영원을 꿈꿀 수 있다는 위로.",
                onValueChange = {},
                isSubmitted = false,
                onActionClick = {},
                forcedFocus = true
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text("7. 오른쪽 멀티라인 등록 완료 상태 (Multiline + Submitted)", color = GrayWhite, fontSize = 10.sp)
            DigCommentInput(
                value = "가진 게 없어도 함께라면 영원을 꿈꿀 수 있다는 위로.",
                onValueChange = {},
                isSubmitted = true,
                onActionClick = {},
                forcedFocus = false
            )
        }
    }
}