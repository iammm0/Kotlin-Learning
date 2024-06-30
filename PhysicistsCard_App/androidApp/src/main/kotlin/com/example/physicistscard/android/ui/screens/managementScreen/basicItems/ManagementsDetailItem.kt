package com.example.physicistscard.android.ui.screens.managementScreen.basicItems

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

data class ManagementDetailItem(
    val managementDetailName: String,
    val managementDetailIcon: ImageVector,
    val managementDetailDescription: String,
    val managementDetailRoute: String
)

@Composable
fun ManagementDetailItem(managementDetailItem: ManagementDetailItem, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(100.dp)
            .clickable{ onClick(managementDetailItem.managementDetailRoute) }, // 点击事件
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = managementDetailItem.managementDetailIcon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column {
                // 设置名称
                Text(
                    text = managementDetailItem.managementDetailName,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                // 设置描述
                Text(
                    text = managementDetailItem.managementDetailDescription,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}

// 用于展示设置列表的Composable  onClick = { onClick(managementDetailItem) }
@Composable
fun ManagementDetailList(managements: List<ManagementDetailItem>, navController: NavController) {
    Column {
        managements.forEach { managementDetailItem ->
            ManagementDetailItem(managementDetailItem = managementDetailItem) { route ->
                navController.navigate(route)
            }
            Spacer(modifier = Modifier.height(1.dp))
        }
    }
}