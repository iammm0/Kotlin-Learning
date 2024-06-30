package com.example.physicistscard.android.ui.screens.managementScreen.storeManagement

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailItem
import com.example.physicistscard.android.ui.screens.managementScreen.basicItems.ManagementDetailList

@Composable
fun StoreManagementsScreen(navController: NavController) {
    val managementDetailItems = listOf<ManagementDetailItem>(
        ManagementDetailItem("购物卡袋", Icons.Outlined.Shop,"右划商品条目即可划入购物卡袋哦！", "store-card-bag"),
        ManagementDetailItem("物流状态", Icons.Rounded.LocationOn, "来看看刚买的卡牌都已经邮到哪里了","store-delivery-status"),
        ManagementDetailItem("订单详情", Icons.Outlined.Menu, "一手交钱一手交货哈哈～", "store-order-detail"),
    )

    Column {
        TopAppBar(text = "商城消息")
        ManagementDetailList(managementDetailItems, navController)
    }
}