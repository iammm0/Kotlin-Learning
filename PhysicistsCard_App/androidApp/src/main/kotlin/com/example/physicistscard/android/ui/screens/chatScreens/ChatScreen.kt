package com.example.physicistscard.android.ui.screens.chatScreens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material.icons.filled.ControlPoint
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ChatUI(onSend: (String) -> Unit, navController: NavController,friendId: String?) {
    var text by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxHeight()) {
        MessagesList(
            messages = listOf("Hello!", "Hi, how are you?", "I'm fine, thanks for asking!"),
            modifier = Modifier.weight(1f),
            navController = navController,
            friendId = friendId
        )
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = {
                    onSend(text)
                    text = ""
                }),
                shape = RoundedCornerShape(30.dp), // 设置圆角形状
            )
            IconButton(
                onClick = {
                    onSend(text)
                    text = ""
                }
            ) {
                Icon(Icons.AutoMirrored.TwoTone.Send, contentDescription = "Send")
            }
        }
        Spacer(Modifier.height(14.dp))
    }
}


@Composable
fun MessagesList(messages: List<String>, modifier: Modifier = Modifier, navController: NavController, friendId: String?) {
    LazyColumn(modifier = modifier.padding(16.dp)) {
        items(messages) { message ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { navController.navigate("friend_profile/$friendId") }
            ) {
                Text(message, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}