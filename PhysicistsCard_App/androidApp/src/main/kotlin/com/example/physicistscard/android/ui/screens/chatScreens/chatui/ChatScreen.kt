import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.twotone.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.physicistscard.android.ui.screens.chatScreens.chatui.MessagesList
import com.example.physicistscard.transmissionModels.auth.user.Role
import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.transmissionModels.community.Message
import com.example.physicistscard.transmissionModels.community.MessageType
import kotlinx.datetime.toLocalDateTime

@Composable
fun ChatUI(
    onSend: (Message) -> Unit,
    navController: NavController,
    currentUser: User,
    friendId: String?
) {
    // 模拟获取好友信息
    val friend = getUserById(friendId) ?: return // 获取好友信息

    var text by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() } // 消息列表

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 16.dp)
    ) {
        MessagesList(
            messages = messages,
            currentUser = currentUser,
            navController = navController,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (text.isNotEmpty()) {
                            val message = Message(
                                messageId = generateMessageId(),
                                sender = currentUser,
                                receiver = friend,
                                content = text,
                                messageType = MessageType.TEXT,
                                timestamp = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                            )
                            messages.add(message)
                            onSend(message)
                            text = ""
                        }
                    }
                ),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.DarkGray,
                    unfocusedContainerColor = Color.DarkGray,
                    disabledContainerColor = Color.DarkGray,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
            )
            IconButton(
                onClick = {
                    if (text.isNotEmpty()) {
                        val message = Message(
                            messageId = generateMessageId(),
                            sender = currentUser,
                            receiver = friend,
                            content = text,
                            messageType = MessageType.TEXT,
                            timestamp = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                        )
                        messages.add(message)
                        onSend(message)
                        text = ""
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.TwoTone.Send,
                    contentDescription = "Send",
                    tint = Color.LightGray
                )
            }
        }
    }
}

// 模拟生成唯一的消息ID
fun generateMessageId(): String {
    return System.currentTimeMillis().toString()
}

// 模拟通过ID获取用户
fun getUserById(userId: String?): User? {
    // 这里应该是从数据源或API获取实际用户信息
    return User(
        userId = "1",
        username = "friend_username",
        displayName = "Friend Name",
        email = "friend@example.com",
        phone = "1234567890",
        passwordHash = "hashed_password",
        avatarUrl = null,
        bio = "This is a bio",
        isOnline = true,
        lastActive = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        registerDate = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault()),
        isEmailVerified = true,
        isPhoneVerified = true,
        role = Role.USER
    )
}






