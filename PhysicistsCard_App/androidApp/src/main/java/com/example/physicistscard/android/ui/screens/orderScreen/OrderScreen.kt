package com.example.physicistscard.android.ui.screens.orderScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.OrderDetailTopBar
import com.example.physicistscard.android.ui.screens.orderScreen.basicItem.Order
import com.example.physicistscard.android.ui.screens.orderScreen.basicItem.OrderItem

val sampleOrders = listOf(
    Order(
        orderId = "123456",
        orderStatus = "待付款",
        items = listOf(
            OrderItem(productId = "P001", productName = "伽利略·比萨斜塔实验·精装", productPrice = 19.0, quantity = 2),
            OrderItem(productId = "P002", productName = "钱学森·力学手稿卡袋·精装", productPrice = 49.0, quantity = 1)
        ),
        totalAmount = 400.0,
        orderTime = "2024-02-23 12:34",
        deliveryAddress = "河南省鹤壁市浚县黎阳街道\n东沙地村第0890号",
        paymentMethod = "信用卡支付"
    ),
    Order(
        orderId = "789012",
        orderStatus = "待发货",
        items = listOf(
            OrderItem(productId = "P003", productName = "爱因斯坦·150周年·纪念·套装", productPrice = 150.0, quantity = 1),
            OrderItem(productId = "P004", productName = "牛顿500周年·纪念·限定收藏·单卡", productPrice = 250.0, quantity = 1)
        ),
        totalAmount = 400.0,
        orderTime = "2024-02-26 15:20",
        deliveryAddress = "天津市西青区津静路26号\n天津城建大学",
        paymentMethod = "在线支付"
    )
)


@Composable
fun OrderScreen(orders: List<Order>, navController: NavController) {
    val orderStatus = listOf("全部","待付款", "待发货", "待收货", "待评价")
    val (selectedOrderStatus, setSelectedOrderStatus) = remember { mutableStateOf(orderStatus.first()) }

    Column {
        OrderDetailTopBar(text = "订单", navController = navController) {

        }

        // 订单状态标签
        ScrollableTabRow(
            selectedTabIndex = orderStatus.indexOf(selectedOrderStatus),
            edgePadding = 16.dp,
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxWidth()
        ) {
            orderStatus.forEachIndexed { _, status ->
                Tab(
                    selected = selectedOrderStatus == status,
                    onClick = { setSelectedOrderStatus(status) },
                    text = {
                        Text(
                            status,
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = if (selectedOrderStatus == status) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.secondary
                            )
                        )
                    }
                )
            }
        }

        // 根据选中的订单状态展示订单列表
        LazyColumn(contentPadding = PaddingValues(bottom = 56.dp + 8.dp)) {
            // 当选中"全部"时，不筛选订单；否则，筛选与选中状态相匹配的订单
            val filteredOrders = if (selectedOrderStatus == "全部") {
                orders
            } else {
                orders.filter { it.orderStatus == selectedOrderStatus }
            }

            items(filteredOrders) { order ->
                OrderItem(order = order, onClick = {
                    // 这里定义点击订单项后的行为，比如导航到订单详情页
                }, navController = navController)
            }
        }
    }
}
