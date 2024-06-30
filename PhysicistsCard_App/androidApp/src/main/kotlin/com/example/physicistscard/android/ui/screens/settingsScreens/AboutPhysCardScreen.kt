package com.example.physicistscard.android.ui.screens.settingsScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun AboutPhysCardScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar("关于PhysCard")
        Spacer(modifier = Modifier.height(16.dp))
        Text("PhysCard 是一个")
        // 其他关于 PhysCard 的信息
    }
}
