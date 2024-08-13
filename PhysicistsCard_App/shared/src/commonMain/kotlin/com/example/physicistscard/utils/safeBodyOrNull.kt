package com.example.physicistscard.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse

suspend inline fun <reified T> HttpResponse.safeBodyOrNull(): T? {
    return try {
        this.body<T>()
    } catch (e: Throwable) {
        null
    }
}
