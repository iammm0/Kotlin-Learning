package com.example.routes.community

import com.example.services.dataAccessServices.community.IFriendshipService
import com.example.services.dataAccessServices.community.IMessageService
import com.example.utils.WebSocketManager
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.serialization.json.Json
import java.time.Duration


fun Application.chatRoutes(
    messageService: IMessageService,
    friendshipService: IFriendshipService
) {
    install(WebSockets) { // 配置WebSocket
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
        pingPeriod = Duration.ofMinutes(1)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
    }

    val webSocketManager = WebSocketManager(messageService)

    routing {
        webSocket("/chat") {
            val userId = call.parameters["userId"] ?: return@webSocket close(CloseReason(CloseReason.Codes.CANNOT_ACCEPT, "Invalid User ID"))

            // 用户连接，保存会话
            webSocketManager.connect(userId, this)

            // 处理用户的消息帧
            webSocketManager.handleIncomingFrames(userId, this)
        }
    }

    routing {
        authenticate {
            post("/send-friend-request") {
                val principal = call.principal<JWTPrincipal>() ?: return@post call.respond(HttpStatusCode.Unauthorized)
                val senderId = principal.payload.getClaim("userId").asString()
                val receiverId = call.receive<String>()

                val result = friendshipService.sendFriendRequest(senderId, receiverId)
                if (result) {
                    call.respond(HttpStatusCode.OK, "好友请求已发送")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "好友请求已存在或其他错误")
                }
            }

            post("/accept-friend-request") {
                val principal = call.principal<JWTPrincipal>() ?: return@post call.respond(HttpStatusCode.Unauthorized)
                val userId = principal.payload.getClaim("userId").asString()
                val friendId = call.receive<String>()

                val result = friendshipService.acceptFriendRequest(userId, friendId)
                if (result) {
                    call.respond(HttpStatusCode.OK, "好友请求已接受")
                } else {
                    call.respond(HttpStatusCode.BadRequest, "接受好友请求失败")
                }
            }

            get("/friends") {
                val principal = call.principal<JWTPrincipal>() ?: return@get call.respond(HttpStatusCode.Unauthorized)
                val userId = principal.payload.getClaim("userId").asString()

                val friends = friendshipService.getFriends(userId)
                call.respond(HttpStatusCode.OK, friends)
            }
        }
    }
}