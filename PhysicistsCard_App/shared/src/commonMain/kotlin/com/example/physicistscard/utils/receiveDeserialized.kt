package com.example.physicistscard.utils

import io.ktor.websocket.Frame
import io.ktor.websocket.WebSocketSession
import io.ktor.websocket.readText
import kotlinx.serialization.json.Json


suspend inline fun <reified T> WebSocketSession.receiveDeserialized(): T {
    val frame = incoming.receive() as? Frame.Text ?: throw IllegalArgumentException("Unexpected frame type")
    return Json.decodeFromString(frame.readText())
}