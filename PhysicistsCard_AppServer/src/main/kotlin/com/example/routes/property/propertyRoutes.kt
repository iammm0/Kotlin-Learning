package com.example.routes.property

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.propertyRoutes() {
    routing {
        route("/user/") {
            // 获取用户的个人信息
            get("/profile") {

            }
            // 更新用户的个人信息
            put("/profile") {

            }

            route("/{userId}") {
                // 获取特定用户发布的所有推送
                get("/") {

                }
                // 获取用户所收藏的所有推送
                get("/community/favorite") {

                }
                // 获取用户所点赞的所有推送
                get("/community/like") {

                }
                // 获取用户所收藏的所有商品
                get("/store/favorite") {

                }
                // 获取用户所点赞的所有商品
                get("/store/like") {

                }
            }
        }
        // 获取app的用户偏好设置，如通知开关、语言选择等
        get("/settings") {

        }
        // 更新用户的app设置
        put("/settings") {

        }
    }
}