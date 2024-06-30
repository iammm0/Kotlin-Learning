package com.example.physicistscard.android.ui.screens.deliveryScreen

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.deliveryScreen.basicItem.DeliveryInfoItem
import com.example.physicistscard.android.ui.screens.deliveryScreen.basicItem.LogisticsInfo

val sampleLogisticsInfo = listOf(
    LogisticsInfo("2023-03-15 10:00", "仓库", "包裹正在等待出库"),
    LogisticsInfo("2023-03-15 15:00", "发货中心", "包裹已出库"),
    LogisticsInfo("2023-03-16 09:00", "运输中", "包裹正在运输中"),
    LogisticsInfo("2023-03-17 08:00", "配送站", "包裹已到达配送站"),
    LogisticsInfo("2023-03-17 14:00", "客户", "包裹已成功派送")
)

@Composable
fun DeliveryDetailsScreen(logisticsInfoList: List<LogisticsInfo>, navController: NavController) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        item {
            TopAppBar(text = "物流详情")
        }
        item {
            Spacer(Modifier.height(2.dp))
        }
        items(logisticsInfoList) { info ->
            DeliveryInfoItem(info)
        }
    }
}