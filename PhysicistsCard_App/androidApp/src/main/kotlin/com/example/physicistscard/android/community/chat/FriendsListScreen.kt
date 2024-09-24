package com.example.physicistscard.android.community.chat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.community.chat.chatui.AvatarImage
import com.example.physicistscard.android.viewModels.FriendshipViewModel
import com.example.physicistscard.transmissionModels.auth.user.User

@Composable
fun UserFriendsScreen(
    navController: NavController,
    friendshipViewModel: FriendshipViewModel = viewModel()
) {
    // 获取好友列表的状态
    val friendsState by friendshipViewModel.friends.collectAsState()

    // 加载好友列表
    friendshipViewModel.loadFriends()

    // 显示好友列表
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        friendsState?.let { result ->
            when {
                result.isSuccess -> {
                    val friendsList = result.getOrNull() ?: emptyList()
                    LazyColumn {
                        items(friendsList) { friend ->
                            FriendItem(friend) {
                                navController.navigate("chat/${friend.userId}")
                            }
                        }
                    }
                }
                result.isFailure -> {
                    Text(
                        text = "加载好友列表失败: ${result.exceptionOrNull()?.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        } ?: run {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun FriendItem(friend: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AvatarImage(avatarUrl = friend.avatarUrl, modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = friend.username, style = MaterialTheme.typography.titleMedium)
            Text(text = friend.bio ?: "", style = MaterialTheme.typography.bodySmall)
        }
    }
}