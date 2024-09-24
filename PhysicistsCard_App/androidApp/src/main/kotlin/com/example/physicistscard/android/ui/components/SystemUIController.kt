package com.example.physicistscard.android.ui.components

import android.os.Build
import android.app.Activity
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun SystemUiController(
    statusBarColor: Color = Color.Transparent,
    navigationBarColor: Color = Color.Transparent,
    isStatusBarVisible: Boolean = true,
    isNavigationBarVisible: Boolean = true,
    isSystemBarsBehaviorTransient: Boolean = true
) {
    val context = LocalContext.current
    val window = (context as Activity).window

    SideEffect {
        // 设置状态栏颜色
        window.statusBarColor = statusBarColor.toArgb()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // 设置DecorFitsSystemWindows为false，允许内容延伸到状态栏
            window.setDecorFitsSystemWindows(false)

            // 设置导航栏颜色
            window.navigationBarColor = navigationBarColor.toArgb()

            // 使用WindowInsetsController
            window.insetsController?.let { controller ->
                if (isStatusBarVisible) {
                    controller.show(WindowInsets.Type.statusBars())
                } else {
                    controller.hide(WindowInsets.Type.statusBars())
                }

                if (isNavigationBarVisible) {
                    controller.show(WindowInsets.Type.navigationBars())
                } else {
                    controller.hide(WindowInsets.Type.navigationBars())
                }

                if (isSystemBarsBehaviorTransient) {
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                } else {
                    controller.systemBarsBehavior =
                        WindowInsetsController.BEHAVIOR_DEFAULT
                }
            }
        } else {
            // 对于Android 11以下的版本，使用旧的API方法
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    )

            // 设置导航栏颜色
            window.navigationBarColor = navigationBarColor.toArgb()

            if (!isStatusBarVisible) {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_FULLSCREEN
            }

            if (!isNavigationBarVisible) {
                @Suppress("DEPRECATION")
                window.decorView.systemUiVisibility =
                    window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }

        // 对于所有版本，确保内容可以延伸至系统栏区域
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
