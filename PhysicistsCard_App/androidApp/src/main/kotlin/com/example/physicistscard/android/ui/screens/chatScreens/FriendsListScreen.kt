package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.components.layouts.TopAppBar
import com.example.physicistscard.android.ui.screens.chatScreens.chatui.AvatarImage
import com.example.physicistscard.transmissionModels.auth.user.User

@Composable
fun UserFriendsScreen(navController: NavController, friends: List<User>) {
    LazyColumn {
        item {
            TopAppBar(text = "好友")
        }
        items(friends) { friend ->
            FriendItem(friend) {
                navController.navigate("chat/${friend.userId}")
            }
        }
    }
}


@Composable
fun FriendItem(friend: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarImage(avatarUrl = friend.avatarUrl, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(friend.displayName ?: friend.username, style = MaterialTheme.typography.bodyLarge)
            Text(
                if (friend.isOnline) "在线" else "最后在线时间: ${friend.lastActive}",
                style = MaterialTheme.typography.bodySmall,
                color = if (friend.isOnline) Color.Green else Color.Gray
            )
        }
    }
}
