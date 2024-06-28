package com.example.physicistscard.android.ui.components

import android.os.Build
import android.app.Activity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@Composable
fun SystemUiController() {
    val context = LocalContext.current
    val window = (context as Activity).window

    SideEffect {
        // 设置状态栏透明
        window.statusBarColor = Color.Transparent.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 设置DecorFitsSystemWindows为false，允许内容延伸到状态栏
            window.setDecorFitsSystemWindows(false)

            // 仅设置导航栏颜色为透明
            window.navigationBarColor = android.graphics.Color.TRANSPARENT

            // 使用WindowInsetsController
            window.insetsController?.let {
                // 保持状态栏可见，但允许内容延伸到状态栏下方
                it.show(WindowInsets.Type.statusBars())
                // 设置系统栏行为：通过滑动可以临时显示隐藏的状态栏
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // 对于Android 11以下的版本，使用旧的API方法
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )

            // 旧方法设置导航栏透明（可选，根据需要决定是否需要）
            @Suppress("DEPRECATION")
            window.navigationBarColor = android.graphics.Color.TRANSPARENT
        }

        // 对于所有版本，确保内容可以延伸至系统栏区域
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
