package com.example.physicistscard

import io.ktor.client.*
import io.ktor.client.engine.darwin.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.*

fun darwinHttpClient() = HttpClient(Darwin) {
    engine {
        configureRequest {
            
        }
        configureSession {

        }
        configureSession(sessionConfig)
        configureRequest(requestConfig)
    }

    defaultRequest {
        // 设置所有请求的默认参数
        header("Accept", "application/json")
    }
}
