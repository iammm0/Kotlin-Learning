package com.example.physicistscard.android.ui.screens.chatScreens.chatui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.transmissionModels.community.Message

@Composable
fun MessagesList(messages: List<Message>, currentUser: User, navController: NavController, modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(messages) { message ->
            MessageBubble(message = message, currentUser = currentUser, navController = navController)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}