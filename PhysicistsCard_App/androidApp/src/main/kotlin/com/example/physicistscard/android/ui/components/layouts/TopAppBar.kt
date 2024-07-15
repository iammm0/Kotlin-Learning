package com.example.physicistscard.android.ui.components.layouts

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(text: String) {
    TopAppBar(
        title = { Text(
            text = text,
            color = MaterialTheme.colorScheme.secondary,
        ) },
        modifier = Modifier.height(75.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background, // TopAppBar背景色
            titleContentColor = MaterialTheme.colorScheme.secondary, // 标题文字颜色
            actionIconContentColor = MaterialTheme.colorScheme.secondary // 动作图标颜色
        )
    )
}