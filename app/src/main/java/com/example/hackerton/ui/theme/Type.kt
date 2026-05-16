package com.example.hackerton.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.hackerton.R

private val Pretendard = FontFamily(
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_semibold, FontWeight.SemiBold),
    Font(R.font.pretendard_bold, FontWeight.Bold),
)

val Typography = Typography(
    displayLarge = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Bold, fontSize = 32.sp, lineHeight = 40.sp),
    headlineLarge = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Bold, fontSize = 24.sp, lineHeight = 32.sp),
    headlineMedium = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, lineHeight = 28.sp),
    titleLarge = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.SemiBold, fontSize = 18.sp, lineHeight = 24.sp),
    titleMedium = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Medium, fontSize = 16.sp, lineHeight = 22.sp),
    bodyLarge = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp),
    bodyMedium = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.25.sp),
    labelLarge = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Medium, fontSize = 14.sp, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    labelSmall = TextStyle(fontFamily = Pretendard, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp),
)

// --- Design Tokens (커스텀 TextStyle) ---
// fontWeight는 디자인 스펙에 미명시 — 사용처에서 .copy(fontWeight = ...)로 조정

val Title = TextStyle(
    fontFamily = Pretendard,
    fontSize = 24.sp,
    lineHeight = 32.sp,
    letterSpacing = (-0.023).em,
)

val Heading = TextStyle(
    fontFamily = Pretendard,
    fontWeight = FontWeight.SemiBold,
    fontSize = 20.sp,
    lineHeight = 28.sp,
    letterSpacing = (-0.012).em,
)

val Headline = TextStyle(
    fontFamily = Pretendard,
    fontSize = 18.sp,
    lineHeight = 26.sp,
    letterSpacing = (-0.002).em,
)

val BodyNormal = TextStyle(
    fontFamily = Pretendard,
    fontSize = 16.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.0057.em,
)

val BodyReading = TextStyle(
    fontFamily = Pretendard,
    fontSize = 16.sp,
    lineHeight = 26.sp,
    letterSpacing = 0.0057.em,
)

val LabelNormal = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.0145.em,
)

val LabelReading = TextStyle(
    fontFamily = Pretendard,
    fontSize = 14.sp,
    lineHeight = 22.sp,
    letterSpacing = 0.0145.em,
)

val Caption = TextStyle(
    fontFamily = Pretendard,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.0252.em,
)
