package com.example.physicistscard.utils

import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

suspend inline fun <reified T> WebSocketSession.sendSerialized(message: T) {
    val jsonString = Json.encodeToString(message)
    send(Frame.Text(jsonString))
}