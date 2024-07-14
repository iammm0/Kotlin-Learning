package com.example.routes


import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import java.util.Collections
import java.util.concurrent.ConcurrentHashMap

fun Application.chatRoutes() {
    val connections = Collections.newSetFromMap(ConcurrentHashMap<DefaultWebSocketServerSession, Boolean>())

    routing {
        route("/community/chat") {
            webSocket {
                connections += this
                try {
                    for (frame in incoming) {
                        if (frame is Frame.Text) {
                            val receivedText = frame.readText()
                            for (connection in connections) {
                                connection.send(Frame.Text(receivedText))
                            }
                        }
                    }
                } finally {
                    connections -= this
                }
            }
        }
    }
}
