package com.example.physicistscard.android.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.Notifications
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(val route: String, val icon: ImageVector, val title: String) {
    Store("store", Icons.Outlined.ShoppingCart, "商城"),
    Community("community", Icons.Outlined.Person, "社区"),
    Management("management", Icons.Outlined.Notifications, "消息"),
    Settings("settings", Icons.Outlined.Settings, "设置")
}
