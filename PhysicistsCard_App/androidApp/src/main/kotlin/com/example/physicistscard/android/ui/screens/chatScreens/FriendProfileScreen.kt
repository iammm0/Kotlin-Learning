package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.physicistscard.android.ui.components.layouts.TopAppBar

@Composable
fun FriendProfileScreen(friendId: String?) {
    LazyColumn {
        item {
            TopAppBar(text = "$friendId 的个人信息")
        }
    }
}
