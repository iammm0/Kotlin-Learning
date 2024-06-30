package com.example.physicistscard.android.ui.components.icons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun AppLogo() {
    // 使用Box来实现居中对齐
    Box(

    ) {
        Text(
            text = "PHYSICISTS\nCARD", // 您的应用标识文本
            style = TextStyle(
                fontSize = 60.sp // 字体大小，根据需要调整
            ),
            textAlign = TextAlign.Center, // 水平居中文本
            color = MaterialTheme.colorScheme.secondary
        )
    }
}
