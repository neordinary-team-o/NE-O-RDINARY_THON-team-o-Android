package com.example.hackerton.util

import android.content.Context

/**
 * 백엔드 사양상 userId는 고정값 "3"으로 보내야 함 (해커톤용 임시 사양).
 * 이전에는 Settings.Secure.ANDROID_ID를 썼으나 변경됨.
 */
object DeviceId {
    @Suppress("UNUSED_PARAMETER")
    fun userId(context: Context): String = "3"
}
