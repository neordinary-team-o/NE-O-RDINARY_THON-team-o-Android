package com.example.hackerton.ui.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hackerton.ui.components.AppTextField
import com.example.hackerton.ui.components.CtaButton
import com.example.hackerton.ui.components.CtaVariant
import com.example.hackerton.ui.theme.BodyNormal
import com.example.hackerton.ui.theme.GrayBlack
import com.example.hackerton.ui.theme.Gray600
import com.example.hackerton.ui.theme.GrayWhite
import com.example.hackerton.ui.theme.HackertonTheme
import com.example.hackerton.ui.theme.Headline
import com.example.hackerton.ui.theme.LabelNormal
import com.example.hackerton.ui.theme.Title

@Composable
fun LoginScreen(
    onLoginClick: (nickname: String, password: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var nickname by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBlack)
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(80.dp))

        // 로고
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(GrayWhite)
                .padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "로고",
                style = Headline.copy(fontWeight = FontWeight.Bold),
                color = GrayBlack,
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text = "어쩌고 슬로건 한줄",
            style = BodyNormal,
            color = Gray600,
            textAlign = TextAlign.Center,
        )

        Spacer(Modifier.height(56.dp))

        // 앱 타이틀
        Text(
            text = "깔롱진스플래시화면",
            style = Title.copy(fontWeight = FontWeight.Bold),
            color = GrayWhite,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.weight(1f))

        // 닉네임
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

        Spacer(Modifier.height(24.dp))

        // 비밀번호
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

        Spacer(Modifier.height(64.dp))

        CtaButton(
            text = "발굴 시작하기",
            onClick = { onLoginClick(nickname, password) },
            variant = CtaVariant.BrightGreen,
            enabled = nickname.isNotEmpty() && password.isNotEmpty(),
        )

        Spacer(Modifier.height(42.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginScreenPreview() {
    HackertonTheme {
        LoginScreen(onLoginClick = { _, _ -> })
    }
}
