package com.example.physicistscard.android.manager.basicItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

data class ManagementItem(
    val managementName: String,
    val managementIcon: ImageVector,
    val description: String = "This is a description", // 假设增加了描述属性
    val route: String
)

@Composable
fun ManagementItem(managementItem: ManagementItem, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick(managementItem.route) }, // 当卡片被点击时，它会调用传入的onClick回调函数，并传递managementItem.route作为参数。
        elevation = 0.dp,
        backgroundColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = managementItem.managementIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                // 设置名称
                Text(
                    text = managementItem.managementName,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 设置描述
                Text(
                    text = managementItem.description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// 用于展示设置列表的Composable
@Composable
fun ManagementList(managements: List<ManagementItem>, navController: NavController) {
    Column {
        managements.forEach { managementItem ->
            ManagementItem(managementItem = managementItem) { route ->
                navController.navigate(route)
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}