package com.example.physicistscard.android.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.physicistscard.businessLogic.IChatService
import com.example.physicistscard.transmissionModels.community.Message
import io.ktor.websocket.WebSocketSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val chatService: IChatService
) : ViewModel() {

    // WebSocket连接状态
    private val _connectionState = MutableStateFlow<Result<WebSocketSession>?>(null)
    val connectionState: StateFlow<Result<WebSocketSession>?> = _connectionState

    // 消息发送状态
    private val _sendMessageState = MutableStateFlow<Result<Unit>?>(null)
    val sendMessageState: StateFlow<Result<Unit>?> = _sendMessageState

    // 消息接收状态
    private val _receiveMessageState = MutableStateFlow<Result<Message>?>(null)
    val receiveMessageState: StateFlow<Result<Message>?> = _receiveMessageState

    // 连接到聊天服务
    fun connectToChat(userId: String) {
        viewModelScope.launch {
            _connectionState.value = try {
                val session = chatService.connectToChat(userId)
                Result.success(session.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 发送消息
    fun sendMessage(session: WebSocketSession, message: Message) {
        viewModelScope.launch {
            _sendMessageState.value = try {
                chatService.sendMessage(session, message)
                Result.success(Unit)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 接收消息
    fun receiveMessage(session: WebSocketSession) {
        viewModelScope.launch {
            _receiveMessageState.value = try {
                val message = chatService.receiveMessage(session)
                Result.success(message.getOrThrow())
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    // 清除状态方法
    fun clearConnectionState() {
        _connectionState.value = null
    }

    fun clearSendMessageState() {
        _sendMessageState.value = null
    }

    fun clearReceiveMessageState() {
        _receiveMessageState.value = null
    }
}
