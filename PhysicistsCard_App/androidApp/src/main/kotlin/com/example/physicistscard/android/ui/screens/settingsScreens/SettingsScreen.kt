package com.example.physicistscard.android.ui.screens.settingsScreens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.sharp.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.physicistscard.android.ui.components.layouts.SettingAndManagementTopAppBar
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailList


@Composable
fun SettingsScreen(navController: NavController) {
    var isDarkThemeEnabled by remember { mutableStateOf(false) }
    var isNotificationsEnabled by remember { mutableStateOf(true) }

    val settings = listOf(
        Setting(settingName = "主题",Icons.Outlined.Face, description = "换个装扮更清爽哦", "setting-theme"),
        Setting(settingName = "语言",Icons.Outlined.Menu,description = "我就知道老外会找这个", "setting-language"),
        Setting(settingName = "反馈",Icons.Outlined.Email, description = "与官方开发者取得联系", "setting-feedback"),
        Setting(settingName = "关于PhysCard",Icons.Outlined.Info, description = "了解PhysCard更多内容？", "setting-appmore")
    )

    val paddingBottom = 56.dp + 36.dp
    LazyColumn(
        // 添加底部内边距
        contentPadding = PaddingValues(bottom = paddingBottom),
        // 其他可能的参数，比如 contentPadding 的 top, start, end
    ) {
        item {
            Column {
                TopAppBar(text = "设置")
                SettingsList(settings = settings) { setting ->
                    navController.navigate(setting.route)
                }
            }
        }
    }
}


