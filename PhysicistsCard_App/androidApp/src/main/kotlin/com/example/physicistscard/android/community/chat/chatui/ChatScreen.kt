import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.physicistscard.android.community.chat.chatui.MessageBubble
import com.example.physicistscard.android.viewModels.ChatViewModel
import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.transmissionModels.community.Message
import com.example.physicistscard.transmissionModels.community.MessageType
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.launch
import kotlinx.datetime.toLocalDateTime

@Composable
fun ChatUI(
    navController: NavController,
    currentUser: User,
    friendId: String,
    chatModel: ChatViewModel = viewModel()
) {
    // WebSocket会话状态
    val connectionState by chatModel.connectionState.collectAsState()
    var session: WebSocketSession? by remember { mutableStateOf(null) }

    // 消息输入状态
    var text by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Message>() }

    val coroutineScope = rememberCoroutineScope()

    // 在组件加载时连接聊天服务
    LaunchedEffect(Unit) {
        chatModel.connectToChat(currentUser.userId)
    }

    // 如果连接成功，则更新session
    connectionState?.let { result ->
        if (result.isSuccess) {
            session = result.getOrNull()
        } else {
            // Handle connection failure (e.g., show an error message)
        }
    }

    Column(
        modifier = Modifier.fillMaxHeight().padding(16.dp)
    ) {
        // 消息列表展示
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true // 最新消息显示在顶部
        ) {
            items(messages) { message ->
                MessageBubble(message, currentUser, navController) // 显示每条消息的气泡
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 消息输入框和发送按钮
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it }, // 更新输入框的值
                modifier = Modifier.weight(1f).padding(end = 8.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(
                    onSend = {
                        if (text.isNotEmpty() && session != null) {
                            val message = Message(
                                messageId = generateMessageId(),
                                senderId = currentUser.userId,
                                receiverId = friendId,
                                content = text,
                                messageType = MessageType.TEXT,
                                timestamp = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                            )
                            messages.add(0, message) // Add message to the top
                            coroutineScope.launch {
                                chatModel.sendMessage(session!!, message)
                            }
                            text = ""
                        }
                    }
                ),
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                )
            )
            IconButton(onClick = {
                if (text.isNotEmpty() && session != null) {
                    val message = Message(
                        messageId = generateMessageId(),
                        senderId = currentUser.userId,
                        receiverId = friendId,
                        content = text,
                        messageType = MessageType.TEXT,
                        timestamp = kotlinx.datetime.Clock.System.now().toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                    )
                    messages.add(0, message)
                    coroutineScope.launch {
                        chatModel.sendMessage(session!!, message)
                    }
                    text = ""
                }
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
            }
        }
    }

    // 监听接收消息
    LaunchedEffect(session) {
        session?.let {
            while (true) {
                val result = chatModel.receiveMessage(it)
                result.let { // 确保result非空后处理
                    when {


                    }
                }
            }
        }
    }
}

// 模拟生成唯一的消息ID
fun generateMessageId(): String {
    return System.currentTimeMillis().toString()
}