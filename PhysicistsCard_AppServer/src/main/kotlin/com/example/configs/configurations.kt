package com.example.configs

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.example.models.transmissionModels.community.Content
import com.example.models.transmissionModels.community.ImageContent
import com.example.models.transmissionModels.community.TextContent
import com.example.models.transmissionModels.community.VideoContent
import com.typesafe.config.ConfigFactory
import io.ktor.http.*
import io.ktor.serialization.kotlinx.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.methodoverride.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.websocket.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import org.slf4j.event.Level
import java.time.Duration

fun Application.configurations() {

    install(AutoHeadResponse)

    install(XHttpMethodOverride)

    install(WebSockets) {
        contentConverter = KotlinxWebsocketSerializationConverter(Json)
        pingPeriod = Duration.ofMinutes(1)
        timeout = Duration.ofSeconds(15)
        maxFrameSize = Long.MAX_VALUE
        masking = false
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

    // 加载配置
    val config = ConfigFactory.load()
    val jwtSecret = config.getString("ktor.security.jwt.secret")
    val jwtIssuer = config.getString("ktor.security.jwt.issuer")
    val jwtRealm = config.getString("ktor.security.jwt.realm")

    val algorithm = Algorithm.HMAC256(jwtSecret)

    install(Authentication) {
        jwt {
            this.realm = jwtRealm
            verifier(
                JWT
                    .require(algorithm)
                    .withIssuer(jwtIssuer)
                    .build()
            )
            validate { credential ->
                if (credential.payload.getClaim("userId").asString().isNotEmpty()) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { _, _ ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }

    // // 配置错误 HTML 响应文件
    // install(StatusPages) {
    //     statusFile(HttpStatusCode.Unauthorized, HttpStatusCode.PaymentRequired, filePattern = "error#.html")
    // }
}
