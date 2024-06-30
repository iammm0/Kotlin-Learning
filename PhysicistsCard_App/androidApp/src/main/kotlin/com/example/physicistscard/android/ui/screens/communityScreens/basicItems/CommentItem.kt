package com.example.physicistscard.android.ui.screens.communityScreens.basicItems

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Comment(
    val userId: String,
    val username: String,
    val commentText: String,
    val timestamp: String, // 可以根据需要添加更多字段
    // val author: String,
    // val replies: MutableList<Comment> = mutableListOf()
)

@Composable
fun CommentCard(
    comment: Comment,
    // onReply: (Comment) -> Unit // 当用户想要回复评论时调用
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {  }, // 点击卡片时触发回复逻辑 onReply(comment)
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary // 自定义卡片的背景颜色
        )
    ) {
        CommentItem(comment = comment)
        // Column(modifier = Modifier.padding(8.dp)) {
        //     // 显示评论本身
        //     CommentItem(comment = comment)
        //     // 使用Spacer创建分隔
        //     Spacer(Modifier.height(8.dp))
//
        //     // 显示评论的回复
        //     comment.replies.forEach { reply ->
        //         CommentItem(comment = reply)
        //         Spacer(Modifier.height(4.dp))
        //     }
        // }
    }
}

@Composable
fun CommentItem(comment: Comment) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = comment.username,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.secondary
            )
        Spacer(Modifier.height(2.dp))
        Text(
            text = comment.commentText,
            color = MaterialTheme.colorScheme.secondary
            )
        Spacer(Modifier.height(4.dp))
        Text(
            text = comment.timestamp,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
            )
        Spacer(modifier = Modifier.height(4.dp))
    }
}