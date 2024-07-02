package com.example.routes.property

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.routing.*

fun Application.propertyRoutes() {
    routing {
        authenticate {
            route("/user") {
                // 获取用户的个人信息
                get("/profile") {
                    // implementation
                }
                // 更新用户的个人信息
                put("/profile") {
                    // implementation
                }

                route("/{userId}") {
                    // 获取特定用户发布的所有推送
                    get("/posts") {
                        // implementation
                    }
                    // 获取用户所收藏的所有推送
                    get("/community/favorites") {
                        // implementation
                    }
                    // 获取用户所点赞的所有推送
                    get("/community/likes") {
                        // implementation
                    }
                    // 获取用户所收藏的所有商品
                    get("/store/favorites") {
                        // implementation
                    }
                    // 获取用户所点赞的所有商品
                    get("/store/likes") {
                        // implementation
                    }
                }
            }
        }

        // 获取app的用户偏好设置，如通知开关、语言选择等
        get("/settings") {
            // implementation
        }
        // 更新用户的app设置
        put("/settings") {
            // implementation
        }
    }
}
