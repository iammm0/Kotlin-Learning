package com.example.routes.store

import com.example.models.transmissionModels.community.CommentTargetType
import com.example.models.transmissionModels.community.UserComment
import com.example.models.transmissionModels.community.FavoriteTargetType
import com.example.models.transmissionModels.community.LikeTargetType
import com.example.models.transmissionModels.store.BagItem
import com.example.models.transmissionModels.store.Order
import com.example.models.transmissionModels.store.PaymentInfo
import com.example.services.dataAccessServices.community.UserCommentService
import com.example.services.dataAccessServices.community.UserFavoriteService
import com.example.services.dataAccessServices.community.UserLikeService
import com.example.services.dataAccessServices.store.OrderService
import com.example.services.dataAccessServices.store.ProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.storeRoutes(
    productService: ProductService,
    orderService: OrderService,
    commentService: UserCommentService,
    likeService: UserLikeService,
    favoriteService: UserFavoriteService
) {
    routing {
        route("/products") {
            get("/") {
                try {
                    call.respond(HttpStatusCode.OK, productService.getAllProducts())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "无法获取产品列表: ${e.localizedMessage}")
                }
            }
            get("/{productId}") {
                val productId = call.parameters["productId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val product = productService.getProductById(productId)
                    if (product != null) {
                        call.respond(HttpStatusCode.OK, product)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到产品")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取产品详情失败: ${e.localizedMessage}")
                }
            }
            get("/search") {
                val query = call.request.queryParameters["query"] ?: return@get call.respond(HttpStatusCode.BadRequest, "缺少搜索关键字")
                try {
                    val products = productService.searchProducts(query)
                    call.respond(HttpStatusCode.OK, products)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "搜索产品失败: ${e.localizedMessage}")
                }
            }
        }

        route("/orders") {
            post("/") {
                try {
                    val order = call.receive<Order>()
                    val newOrder = orderService.createOrder(order)
                    call.respond(HttpStatusCode.Created, newOrder)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "创建订单失败: ${e.localizedMessage}")
                }
            }
            get("/{orderId}") {
                val orderId = call.parameters["orderId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的订单ID")
                try {
                    val order = orderService.getOrderById(orderId)
                    if (order != null) {
                        call.respond(HttpStatusCode.OK, order)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到订单")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取订单详情失败: ${e.localizedMessage}")
                }
            }
            get("/") {
                try {
                    val userId = call.request.queryParameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "缺少用户ID")
                    val orders = orderService.getOrdersByUserId(userId)
                    call.respond(HttpStatusCode.OK, orders)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取用户订单列表失败: ${e.localizedMessage}")
                }
            }
            get("/{orderId}/shipping") {
                val orderId = call.parameters["orderId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的订单ID")
                try {
                    val order = orderService.getOrderById(orderId)
                    if (order?.shippingInfo != null) {
                        call.respond(HttpStatusCode.OK, order.shippingInfo)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到物流信息")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取物流信息失败: ${e.localizedMessage}")
                }
            }
        }

        route("/bags") {
            post("/") {
                try {
                    val bagItem = call.receive<BagItem>()
                    // 添加到购物袋的逻辑
                    call.respond(HttpStatusCode.Created, "商品已添加到购物袋")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "添加商品到购物袋失败: ${e.localizedMessage}")
                }
            }
            get("/") {
                try {
                    val userId = call.request.queryParameters["userId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "缺少用户ID")
                    // 获取购物袋内容的逻辑
                    call.respond(HttpStatusCode.OK, "获取购物袋内容成功")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取购物袋内容失败: ${e.localizedMessage}")
                }
            }
            put("/items/{itemId}") {
                val itemId = call.parameters["itemId"] ?: return@put call.respond(HttpStatusCode.BadRequest, "无效的商品ID")
                try {
                    val quantity = call.receive<Int>()
                    // 更新购物袋中商品数量的逻辑
                    call.respond(HttpStatusCode.OK, "购物袋商品数量更新成功")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "更新购物袋商品数量失败: ${e.localizedMessage}")
                }
            }
            delete("/items/{itemId}") {
                val itemId = call.parameters["itemId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的商品ID")
                try {
                    // 从购物袋中移除商品的逻辑
                    call.respond(HttpStatusCode.OK, "商品已从购物袋移除")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "从购物袋移除商品失败: ${e.localizedMessage}")
                }
            }
        }

        post("/payments") {
            try {
                val paymentInfo = call.receive<PaymentInfo>()
                // 处理支付请求的逻辑
                call.respond(HttpStatusCode.Created, "支付请求已处理")
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "处理支付请求失败: ${e.localizedMessage}")
            }
        }

        route("/products/{productId}") {
            post("/comments") {
                val productId = call.parameters["productId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val comment = call.receive<UserComment>()
                    val newComment = commentService.addComment(comment.copy(targetId = productId, targetType = CommentTargetType.PRODUCT))
                    call.respond(HttpStatusCode.Created, newComment)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "发表评论失败: ${e.localizedMessage}")
                }
            }
            get("/comments") {
                val productId = call.parameters["productId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val comments = commentService.getCommentsByTargetId(productId, CommentTargetType.PRODUCT)
                    call.respond(HttpStatusCode.OK, comments)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取评论列表失败: ${e.localizedMessage}")
                }
            }
            post("/like") {
                val productId = call.parameters["productId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val userId = call.receive<String>()
                    val userLike = likeService.addLike(userId, productId, LikeTargetType.PRODUCT)
                    call.respond(HttpStatusCode.Created, userLike)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "点赞失败: ${e.localizedMessage}")
                }
            }
            delete("/like") {
                val productId = call.parameters["productId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val userId = call.receive<String>()
                    if (likeService.removeLike(userId, productId, LikeTargetType.PRODUCT)) {
                        call.respond(HttpStatusCode.OK, "已取消点赞")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到点赞记录")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "取消点赞失败: ${e.localizedMessage}")
                }
            }
            post("/favorite") {
                val productId = call.parameters["productId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val userId = call.receive<String>()
                    val userFavorite = favoriteService.addFavorite(userId, productId, FavoriteTargetType.PRODUCT)
                    call.respond(HttpStatusCode.Created, userFavorite)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "收藏失败: ${e.localizedMessage}")
                }
            }
            delete("/favorite") {
                val productId = call.parameters["productId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val userId = call.receive<String>()
                    if (favoriteService.removeFavorite(userId, productId, FavoriteTargetType.PRODUCT)) {
                        call.respond(HttpStatusCode.OK, "已取消收藏")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到收藏记录")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "取消收藏失败: ${e.localizedMessage}")
                }
            }
            get("/stats") {
                val productId = call.parameters["productId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                try {
                    val stats = mapOf(
                        "likes" to likeService.countLikes(productId, LikeTargetType.PRODUCT),
                        "comments" to commentService.getCommentsByTargetId(productId, CommentTargetType.PRODUCT).size
                    )
                    call.respond(HttpStatusCode.OK, stats)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取统计信息失败: ${e.localizedMessage}")
                }
            }
        }
    }
}
