package com.example.physicistscard.android.ui.components.buttons

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LoginAndRegisterButton(
    text: String,
    onClick: () -> Unit
) {

    val clicked by remember { mutableStateOf(false) }
    val scale: Float by animateFloatAsState(
        targetValue = if (clicked) 0.9f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = ""
    )

    Button(
        onClick = onClick,
        // 设置按钮的默认颜色
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
        // 设置默认边框颜色和宽度
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        // 设置默认按钮的圆角Color(0x9EB388FF)
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(width = 300.dp, height = 48.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
    ) {
        Text(
            text,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 14.sp, // 设置默认字体大小
                color = MaterialTheme.colorScheme.secondary // 设置默认字体颜色
            )
        )
    }
}
