package com.example.physicistscard.android.ui.screens.settingsScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Setting(
    val settingName: String,
    val settingIcon: ImageVector,
    val description: String = "This is a description",
    val route: String
)

@Composable
fun SettingItem(setting: Setting, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick), // 点击事件
        elevation = CardDefaults.cardElevation(0.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = setting.settingIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
                )
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                // 设置名称
                Text(
                    text = setting.settingName,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                // 设置描述
                Text(
                    text = setting.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// 用于展示设置列表的Composable
@Composable
fun SettingsList(settings: List<Setting>, onClick: (Setting) -> Unit) {
    Column {
        settings.forEach { setting ->
            SettingItem(setting = setting, onClick = { onClick(setting) })
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}