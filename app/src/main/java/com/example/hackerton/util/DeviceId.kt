package com.example.hackerton.util

import android.content.Context
import android.provider.Settings

/**
 * 폰마다 고정된 userId(String)를 반환.
 * Settings.Secure.ANDROID_ID를 그대로 사용 — 16자리 hex 문자열.
 * 앱 설치/제거에도 유지되고, 팩토리 리셋 시에만 바뀜.
 */
object DeviceId {
    fun userId(context: Context): String =
        Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID,
        ).orEmpty()
}
