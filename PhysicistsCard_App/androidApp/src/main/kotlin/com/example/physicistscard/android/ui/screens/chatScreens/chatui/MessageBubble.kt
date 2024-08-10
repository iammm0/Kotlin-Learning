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
fun MessageBubble(message: Message, currentUser: User, navController: NavController) {
    val isOwnMessage = message.sender.userId == currentUser.userId
    val backgroundColor = if (isOwnMessage) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
    val alignment = if (isOwnMessage) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (!isOwnMessage) {
            // 左侧消息时显示对方头像
            AvatarImage(avatarUrl = message.sender.avatarUrl) {
                navController.navigate("friend_profile/${message.sender.userId}")
            }
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (isOwnMessage) Alignment.End else Alignment.Start
        ) {
            // 消息内容
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

            // 消息时间
            Text(
                text = message.timestamp.toString(), // 显示时间
                fontSize = 10.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        if (isOwnMessage) {
            Spacer(modifier = Modifier.width(8.dp))
            // 右侧消息时显示自己的头像
            AvatarImage(avatarUrl = message.sender.avatarUrl) {
                navController.navigate("friend_profile/${message.sender.userId}")
            }
        }
    }
}