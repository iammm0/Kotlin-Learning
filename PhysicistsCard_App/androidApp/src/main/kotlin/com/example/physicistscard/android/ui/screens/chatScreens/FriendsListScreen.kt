package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

data class Friend(
    val id: Int,
    val name: String,
    val status: String
)

val friends = listOf(
    Friend(1, "张子洋", "Online"),
    Friend(2, "蒋鸿杰", "Online"),
    Friend(3, "李培梁", "Online"),
    Friend(4, "王芃匀", "Online"),
    Friend(5, "尹炳杰", "Online"),
    Friend(6, "赵明俊", "Online"),
)

@Composable
fun UserFriendsScreen(navController: NavController) {

    LazyColumn {
        item {
            TopAppBar(text = "好友")
        }
        items(friends) {friend ->
            FriendItem(friend) {
                navController.navigate("friend_profile/${friend.id}")
            }
        }
    }
}

@Composable
fun FriendItem(friend: Friend, onClick: (Friend) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(friend) }
            .padding(vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier.weight(1f)) {
                Text(friend.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(friend.status, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}