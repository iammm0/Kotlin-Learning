package com.example.physicistscard.android.ui.screens.managementScreen.communityManagement

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailItem
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailList

@Composable
fun CommunityManagementsScreen(navController: NavController) {
    val managementDetailItems = listOf<ManagementDetailItem>(
        ManagementDetailItem("我的帖子", Icons.Rounded.Email,"来回顾下本世纪最伟大的作品！","community-my-post"),
        ManagementDetailItem("我的好友", Icons.Rounded.AccountCircle,"没有人可以独善其身","community-my-friends"),
        ManagementDetailItem("互动消息", Icons.AutoMirrored.TwoTone.Send, "常交流常联系，小伙伴们才不会走丢哦", "community-interactive-message"),
        ManagementDetailItem("我的收藏", Icons.Outlined.Star, "咱也是个小小收藏家，嘻嘻～", "community-my-favorite"),
    )

    Column {
        TopAppBar(text = "社区管理")
        ManagementDetailList(managementDetailItems, navController)
    }
}