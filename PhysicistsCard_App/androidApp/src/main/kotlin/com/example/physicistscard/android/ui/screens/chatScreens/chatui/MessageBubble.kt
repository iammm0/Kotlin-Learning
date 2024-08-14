package com.example.physicistscard.android.ui.screens.chatScreens.chatui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.transmissionModels.community.Message

@Composable
fun MessageBubble(
    message: Message,
    currentUser: User,
    navController: NavController
) {
    // 判断消息是否是当前用户发送的
    val isOwnMessage = message.senderId == currentUser.userId
    // 根据消息发送者设置背景颜色
    val backgroundColor = if (isOwnMessage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    // 根据消息发送者设置消息排列方向
    val alignment = if (isOwnMessage) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isOwnMessage) {
            // 显示对方的头像
            AvatarImage(avatarUrl = currentUser.avatarUrl) {
                navController.navigate("friend_profile/${message.senderId}")
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isOwnMessage) Alignment.End else Alignment.Start
        ) {
            // 显示消息内容
            Text(
                text = message.content,
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier
                    .background(
                        color = backgroundColor,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
                    .widthIn(min = 50.dp, max = 250.dp)
            )

            // 显示消息的时间戳
            Text(
                text = message.timestamp.toString(),
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (isOwnMessage) {
            Spacer(modifier = Modifier.width(8.dp))
            // 显示当前用户的头像
            AvatarImage(avatarUrl = currentUser.avatarUrl) {
                navController.navigate("friend_profile/${message.senderId}")
            }
        }
    }
}
