package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

data class MessagePreview(
    val id: Int,
    val from: String,
    val preview: String,
    val timestamp: String
)

@Composable
fun UserInteractionsScreen(navController: NavController) {

    val messages = listOf(
        MessagePreview(1, "Albert", "重要的是不断质疑。 我们绝不能失去神圣的好奇心。", "5 min ago"),
        MessagePreview(2, "Newton", "跌倒了，爬起来。", "1 hour ago")
    )

    Column {
        TopAppBar(text = "消息")

        // 搜索栏
        TextField(
            value = "",
            onValueChange = { /* Implement search functionality */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("搜索用户") }
        )

        LazyColumn {
            items(messages) { message ->
                MessageItem(message) {
                    navController.navigate("chat/${message.id}")
                }
            }
        }
    }
}

@Composable
fun MessageItem(message: MessagePreview, onClick: (MessagePreview) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(message) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(10.dp))
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(48.dp)
                .padding(end = 16.dp)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(message.from, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Text(message.preview, style = MaterialTheme.typography.bodyMedium)
            Text(message.timestamp, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
