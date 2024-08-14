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
fun SendFriendRequestScreen(
    friendId: String,
    friendshipViewModel: FriendshipViewModel = viewModel()
) {
    // 监听发送好友请求的状态
    val sendRequestState by friendshipViewModel.sendFriendRequestState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            friendshipViewModel.sendFriendRequest(friendId)
        }) {
            Text(text = "发送好友请求")
        }

        // 处理发送好友请求的状态
        sendRequestState?.let { result ->
            when {
                result.isSuccess -> {
                    Text("好友请求已发送")
                }
                result.isFailure -> {
                    Text(
                        text = "发送好友请求失败: ${result.exceptionOrNull()?.message}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
