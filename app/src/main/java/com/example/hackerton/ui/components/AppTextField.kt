package com.example.hackerton.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.theme.BodyNormal
import com.example.hackerton.ui.theme.Gray600
import com.example.hackerton.ui.theme.Gray800
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.GreenNormal
import com.example.hackerton.ui.theme.HackertonTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier.width(320.dp),
    placeholder: String = "",
    singleLine: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val selectionColors = TextSelectionColors(
        handleColor = GreenNormal,
        backgroundColor = GreenNormal.copy(alpha = 0.3f),
    )

    CompositionLocalProvider(LocalTextSelectionColors provides selectionColors) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier
                .clip(CircleShape)
                .background(Gray800)
                .border(
                    width = 1.dp,
                    color = if (isFocused) GreenNormal else Gray800,
                    shape = CircleShape,
                )
                .padding(horizontal = 20.dp, vertical = 16.dp),
            textStyle = BodyNormal.copy(color = GrayWhite),
            cursorBrush = SolidColor(GreenNormal),
            singleLine = singleLine,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    if (leadingIcon != null) leadingIcon()
                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text = placeholder,
                                style = BodyNormal.copy(color = Gray600),
                            )
                        }
                        innerTextField()
                    }
                    if (trailingIcon != null) trailingIcon()
                }
            },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1C1B1F)
@Composable
private fun AppTextFieldPreview() {
    HackertonTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            // default
            AppTextField(
                value = "",
                onValueChange = {},
                placeholder = "예: 온라인 교육 플랫폼 만족도 조사",
            )
            // filled
            AppTextField(
                value = "2025 직장인 워라밸 실태조사",
                onValueChange = {},
                placeholder = "예: 온라인 교육 플랫폼 만족도 조사",
            )
            // 검색 (leading)
            AppTextField(
                value = "",
                onValueChange = {},
                placeholder = "검색",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.find_icon),
                        contentDescription = null,
                        tint = Gray600,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )
            // 검색 + clear (leading + trailing)
            AppTextField(
                value = "낙엽",
                onValueChange = {},
                placeholder = "검색",
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.find_icon),
                        contentDescription = null,
                        tint = GrayWhite,
                        modifier = Modifier.size(20.dp),
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear",
                        tint = Gray600,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )
            // valid (unselected variant)
            AppTextField(
                value = "낙엽",
                onValueChange = {},
                placeholder = "",
                trailingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.check_icon),
                        contentDescription = "Valid",
                        tint = GreenNormal,
                        modifier = Modifier.size(20.dp),
                    )
                },
            )
        }
    }
}
