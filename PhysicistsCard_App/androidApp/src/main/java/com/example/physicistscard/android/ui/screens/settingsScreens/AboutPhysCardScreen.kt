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
    TopAppBar("关于PhysCard")

    Column(modifier = Modifier.padding(16.dp)) {
        TopAppBar("反馈")
        Spacer(modifier = Modifier.height(16.dp))
        Text("PhysCard 是一个为那些在过去，现在以及将来热爱着自然科学的人们所缔造的乌托邦。")
        // 其他关于 PhysCard 的信息
    }
}
