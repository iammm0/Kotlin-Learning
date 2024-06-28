package com.example.physicistscard.android.ui.screens.orderScreen.basicItem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.navigation.BottomNavItem

data class OrderItem(
    val productId: String,
    val productName: String,
    val productPrice: Double,
    val quantity: Int
)

data class Order(
    val orderId: String,
    val orderStatus: String,
    val items: List<OrderItem>,
    val totalAmount: Double,
    val orderTime: String, // 实际应用中可能使用Date或者其他时间类型
    val deliveryAddress: String,
    val paymentMethod: String
)


@Composable
fun OrderItem(order: Order,
              navController: NavController,
              onClick: (String) -> Unit
              ) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                // 导航到订单详情页，并传递商品ID
                navController.navigate("orderDetail/${order.orderId}")
            }
            .fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column {

            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = { navController.navigate(BottomNavItem.Store.route) },
                    // 设置默认边框颜色和宽度
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                ) {
                    Text(
                        text = "大天而思·官方商城",
                        style = TextStyle(
                            fontSize = 14.sp, // 设置默认字体大小
                            color = MaterialTheme.colorScheme.secondary // 设置默认字体颜色
                        )
                    )
                }
                Spacer(Modifier.width(66.dp))
                Text(
                    text = order.orderStatus,
                    color = MaterialTheme.colorScheme.secondary
                    )
            }

            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
                    .height(120.dp)
                    .clickable( onClick = { navController.navigate("store-delivery-status") } ), // 当卡片被点击时，它会调用传入的onClick回调函数，并传递managementItem.route作为参数。
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Spacer(Modifier.height(18.dp))
                    Text(
                        text = order.deliveryAddress,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = order.orderTime,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
            
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 假设的商品图片，实际中应替换为Image组件
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(color = Color.LightGray)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    // 订单描述
                    val productNames = order.items.joinToString(separator = "/n") { it.productName }
                    Text(
                        text = productNames,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

            }
        }
    }
}
