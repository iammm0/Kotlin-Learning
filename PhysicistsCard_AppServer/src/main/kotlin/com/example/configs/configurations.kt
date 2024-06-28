package com.example.configs

import com.example.models.transmissionModels.community.Content
import com.example.models.transmissionModels.community.ImageContent
import com.example.models.transmissionModels.community.TextContent
import com.example.models.transmissionModels.community.VideoContent
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.methodoverride.*
import io.ktor.server.request.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.slf4j.event.Level

fun Application.configurations() {

    install(AutoHeadResponse)

    install(XHttpMethodOverride)

    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
    }

    // 配置日志记录设置
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    // 配置JSON序列化
    val module = SerializersModule {
        polymorphic(Content::class) {
            subclass(TextContent::class)
            subclass(ImageContent::class)
            subclass(VideoContent::class)
        }
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true  // 忽略未知键，避免解析错误
            coerceInputValues = true  // 尝试转换不匹配的值而不是抛出异常
            useArrayPolymorphism = true // 启用数组多态性
            serializersModule = module
            classDiscriminator = "type" // 更改鉴别器字段名，避免与数据类字段冲突
        })
    }

    // // 配置JWT认证
    // val secret = environment.config.property("jwt.secret").getString()
    // val issuer = environment.config.property("jwt.issuer").getString()
    // val audience = environment.config.property("jwt.audience").getString()
    // val myRealm = environment.config.property("jwt.realm").getString()
//
    // install(Authentication) {
    //     jwt("auth-jwt") {
    //         realm = myRealm
    //         verifier(
    //             JWT
    //             .require(Algorithm.HMAC256(secret))
    //             .withAudience(audience)
    //             .withIssuer(issuer)
    //             .build())
    //     }
    // }
//
    // // 配置错误 HTML 响应文件
    // install(StatusPages) {
    //     statusFile(HttpStatusCode.Unauthorized, HttpStatusCode.PaymentRequired, filePattern = "error#.html")
    // }
}
