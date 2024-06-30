package com.example.physicistscard.android.ui.screens.orderScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun OrderDetail(orderId: String?, navController: NavController) {
   LazyColumn(
       modifier = Modifier.padding(vertical = 42.dp)
   ) {

       item {
           // 记录内容是否被展开
           var isExpanded by remember { mutableStateOf(false) }
           Card(
               modifier = Modifier
                   .padding(8.dp)
                   .fillMaxWidth(),
               shape = RoundedCornerShape(8.dp),
               colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
               elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
           ) {
               Column(
                   modifier = Modifier
                       .clickable { isExpanded = !isExpanded }
                       .padding(16.dp)
               ) {
                   Text(
                       text = "点击查看更多信息",
                       fontWeight = FontWeight.Bold,
                       color = MaterialTheme.colorScheme.secondary
                       )

                   // 使用AnimatedVisibility来平滑过渡内容的显示和隐藏
                   AnimatedVisibility(visible = isExpanded) {
                       Column {
                           Text(
                               text = "这里是详细内容，点击按钮可以折叠。",
                               color = MaterialTheme.colorScheme.secondary
                           )
                           // 添加更多内容
                       }
                   }

                   // 展开/折叠按钮
                   Text(
                       text = if (isExpanded) "收起" else "展开",
                       modifier = Modifier
                           .align(Alignment.End)
                           .padding(top = 8.dp),
                       color = MaterialTheme.colorScheme.secondary
                   )
               }
           }
       }
       item {
           Card {

           }
       }
   }
}