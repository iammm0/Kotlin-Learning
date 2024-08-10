package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import getUserById

@Composable
fun FriendProfileScreen(navController: NavController, friendId: String?) {
    val friend = getUserById(friendId) ?: return // 获取好友信息

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        TopAppBar(text = "${friend.displayName ?: friend.username} 的个人信息")

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "用户名: ${friend.username}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // 显示其他用户信息
        Text(text = "邮箱: ${friend.email ?: "无"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "电话: ${friend.phone ?: "无"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "简介: ${friend.bio ?: "无"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "在线状态: ${if (friend.isOnline) "在线" else "离线"}", style = MaterialTheme.typography.bodyLarge)
        Text(text = "最后在线时间: ${friend.lastActive ?: "未知"}", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(onClick = { /* Handle add friend */ }) {
                Text("添加好友")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { /* Handle delete friend */ }) {
                Text("删除好友")
            }
        }
    }
}

