package com.example.routes.merchantRoutes

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.merchantRoutes() {
    routing {
        route("/merchant/products") {
            // 允许商家添加新商品到商城
            post("/") {

            }
            // 允许商家编辑已发布商品的详细信息
            put("/{productId}") {

            }
            // 允许商家删除已发布的商品
            delete("/{productId}") {

            }
            // 获取商家自己发布的商品列表
            get("/") {

            }
        }
    }

    routing {
        route("/merchant/orders") {
            // 允许商家查看所有与自己商品相关的订单信息
            get("/") {

            }
            // 允许商家更新订单状态（比如，标记为已发货）
            put("/{orderId}/status") {

            }
        }
    }

    routing {
        route("/merchant/payments/{orderId}") {
            // 允许商家查看特定订单的支付状态
            get("/") {

            }
            // 商家发货，并提供物流信息
            post("/shipping") {

            }
            // 更新已发货订单的物流信息
            put("/shipping") {

            }
        }
    }

    routing {
        route("/merchant/analytics") {
            // 提供销售数据分析，帮助商家了解销售趋势
            get("/sales") {

            }
            // 分析各商品的浏览量和销量，确定热门商品
            get("/popularity") {

            }
        }
    }

    routing {
        route("/merchant") {
            // 允许商家创建针对自己商品的促销活动
            post("/promotions") {

            }
            // 创建新的优惠券
            post("/coupons") {

            }
            // 查看已创建的优惠券列表
            get("/coupons") {

            }
        }
    }

}