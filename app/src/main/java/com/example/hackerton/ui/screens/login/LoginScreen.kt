package com.example.hackerton.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.R
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.CtaButton
import com.example.hackerton.ui.components.CtaVariant
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.LabelNormal

@Composable
fun LoginScreen(
    onLoginClick: (nickname: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack)
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
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(Modifier.height(114.dp))

            Image(
                painter = painterResource(id = R.drawable.logo_hongdae),
                contentDescription = null,
                modifier = Modifier.size(width = 160.dp, height = 38.dp),
            )

            Spacer(Modifier.height(32.dp))

            Image(
                painter = painterResource(id = R.drawable.sub_logo),
                contentDescription = null,
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "닉네임",
                style = LabelNormal.copy(fontWeight = FontWeight.Medium),
                color = GrayWhite,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            AppTextField(
                value = nickname,
                onValueChange = { nickname = it },
                placeholder = "닉네임을 입력해주세요.",
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "비밀번호",
                style = LabelNormal.copy(fontWeight = FontWeight.Medium),
                color = GrayWhite,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(Modifier.height(8.dp))
            AppTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = "비밀번호를 입력해주세요.",
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            )

            Spacer(Modifier.height(48.dp))

            CtaButton(
                text = "발굴 시작하기",
                onClick = { onLoginClick(nickname, password) },
                variant = CtaVariant.BrightGreen,
                enabled = nickname.isNotEmpty() && password.isNotEmpty(),
            )

            Spacer(Modifier.height(42.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HackertonTheme {
        LoginScreen(onLoginClick = { _, _ -> })
    }
}
