package com.example.physicistscard.android.ui.screens.managementScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.List
import androidx.compose.material.icons.outlined.PermMedia
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.SettingAndManagementTopAppBar
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementItem
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementList

@Composable
fun ManagementScreen(navController: NavController) {
    val managementItems = listOf<ManagementItem>(
        ManagementItem("商城",Icons.Outlined.ShoppingCart,"购物卡袋  物流状态  订单详情","store-management-detail"),
        ManagementItem("社区",Icons.Outlined.PermMedia, "我的帖子  我的好友  互动消息\n我的收藏","community-management-detail"),
        ManagementItem("账户",Icons.AutoMirrored.TwoTone.List, "切换头像  绑定手机  绑定邮箱\n重置密码  注销账户  添加账户","account-management-detail"),
    )

    Column {
        SettingAndManagementTopAppBar(text = "消息") {}
        ManagementList(managementItems, navController)
    }
}