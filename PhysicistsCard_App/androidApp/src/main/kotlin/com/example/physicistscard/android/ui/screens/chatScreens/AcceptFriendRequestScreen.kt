package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.physicistscard.android.viewModels.FriendshipViewModel

@Composable
fun AcceptFriendRequestScreen(
    friendId: String,
    friendshipViewModel: FriendshipViewModel = viewModel()
) {
    // 监听接受好友请求的状态
    val acceptRequestState by friendshipViewModel.acceptFriendRequestState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            friendshipViewModel.acceptFriendRequest(friendId)
        }) {
            Text(text = "接受好友请求")
        }

        // 处理接受好友请求的状态
        acceptRequestState?.let { result ->
            when {
                result.isSuccess -> {
                    Text("好友请求已接受")
                }
                result.isFailure -> {
                    Text(
                        text = "接受好友请求失败: ${result.exceptionOrNull()?.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
