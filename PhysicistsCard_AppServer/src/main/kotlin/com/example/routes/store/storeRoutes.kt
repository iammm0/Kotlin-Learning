package com.example.routes.store

import com.example.models.transmissionModels.community.interaction.*
import com.example.models.transmissionModels.store.bag.BagItem
import com.example.models.transmissionModels.store.order.Order
import com.example.models.transmissionModels.store.payment.PaymentInfo
import com.example.services.dataAccessServices.community.*
import com.example.services.dataAccessServices.store.IOrderService
import com.example.services.dataAccessServices.store.IProductService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.time.LocalDateTime
import java.util.*

fun Application.storeRoutes(
    productService: IProductService,
    orderService: IOrderService,
    commentService: IUserCommentService,
    likeService: IUserLikeService,
    favoriteService: IUserFavoriteService
) {
    routing {
        authenticate {
            route("/store/products") {
                // 浏览商品栏目
                get("/") {
                    try {
                        call.respond(HttpStatusCode.OK, productService.getAllProducts())
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "无法获取产品列表: ${e.localizedMessage}")
                    }
                }

                // 获取特定商品详情
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

                // 搜索和筛选商品
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

            route("/store/orders") {
                // 创建订单
                post("/") {
                    try {
                        val order = call.receive<Order>()
                        val newOrder = orderService.createOrder(order)
                        call.respond(HttpStatusCode.Created, newOrder)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "创建订单失败: ${e.localizedMessage}")
                    }
                }
                // 获取特定订单详情
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
                // 获取订单列表
                get("/") {
                    try {
                        val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                            ?: return@get call.respond(HttpStatusCode.BadRequest, "缺少用户ID")
                        val orders = orderService.getOrdersByUserId(userId)
                        call.respond(HttpStatusCode.OK, orders)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "获取用户订单列表失败: ${e.localizedMessage}")
                    }
                }
                // 获取特定订单的物流信息
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

            // 购物车相关的路由
            route("/store/bags") {
                post("/") {
                    // 创建购物车袋的逻辑
                }
                post("/{cartId}/add") {
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
                        val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                            ?: return@get call.respond(HttpStatusCode.BadRequest, "缺少用户ID")
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

            // 处理支付请求
            post("/payments") {
                try {
                    val paymentInfo = call.receive<PaymentInfo>()
                    // 处理支付请求的逻辑
                    call.respond(HttpStatusCode.Created, "支付请求已处理")
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, "处理支付请求失败: ${e.localizedMessage}")
                }
            }

            route("/store/products/{productId}") {
                // 发表评论
                post("/comments") {
                    val productId = call.parameters["productId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                        ?: return@post call.respond(HttpStatusCode.BadRequest, "用户ID无效")
                    try {
                        // 接收 UserCommentRequest
                        val commentRequest = call.receive<UserCommentRequest>()
                        // 将 UserCommentRequest 转换为 UserComment
                        val comment = UserComment(
                            commentId = UUID.randomUUID().toString(),  // 自动生成 commentId
                            userId = userId,  // 从 JWT 中提取的 userId
                            targetId = productId,  // 使用 productId 作为目标ID
                            targetType = CommentTargetType.PRODUCT,  // 目标类型为 PRODUCT
                            content = commentRequest.content,  // 请求体中的评论内容
                            createdAt = LocalDateTime.now(),  // 设置创建时间
                            parentId = commentRequest.parentId  // 请求体中的父评论ID（可选）
                        )

                        val added = commentService.addComment(productId, comment)
                        if (added) {
                            call.respond(HttpStatusCode.Created, "评论已添加")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "评论添加失败")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "添加评论失败: ${e.localizedMessage}")
                    }
                }
                // 获取所有评论
                get("/comments") {
                    val productId = call.parameters["productId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    try {
                        val comments = commentService.getCommentsByTargetId(productId, CommentTargetType.PRODUCT)
                        call.respond(HttpStatusCode.OK, comments)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "获取评论列表失败: ${e.localizedMessage}")
                    }
                }
                // 点赞
                post("/like") {
                    val productId = call.parameters["productId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                        ?: return@post call.respond(HttpStatusCode.BadRequest, "用户ID无效")
                    try {
                        val userLike = likeService.addLike(userId, productId, LikeTargetType.PRODUCT)
                        if (userLike != null) {
                            call.respond(HttpStatusCode.Created, userLike)
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "点赞失败，无法创建点赞记录")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "点赞失败: ${e.localizedMessage}")
                    }
                }
                // 取消点赞
                delete("/like") {
                    val productId = call.parameters["productId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                        ?: return@delete call.respond(HttpStatusCode.BadRequest, "用户ID无效")
                    try {
                        if (likeService.removeLike(userId, productId, LikeTargetType.PRODUCT)) {
                            call.respond(HttpStatusCode.OK, "已取消点赞")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "未找到点赞记录")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "取消点赞失败: ${e.localizedMessage}")
                    }
                }
                // 收藏
                post("/favorite") {
                    val productId = call.parameters["productId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                        ?: return@post call.respond(HttpStatusCode.BadRequest, "用户ID无效")
                    try {
                        val userFavorite = favoriteService.addFavorite(userId, productId, FavoriteTargetType.PRODUCT)
                        call.respond(HttpStatusCode.Created, userFavorite!!)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.BadRequest, "收藏失败: ${e.localizedMessage}")
                    }
                }

                // 取消收藏
                delete("/favorite") {
                    val productId = call.parameters["productId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    val userId = call.principal<JWTPrincipal>()?.payload?.getClaim("userId")?.asString()
                        ?: return@delete call.respond(HttpStatusCode.BadRequest, "用户ID无效")
                    try {
                        if (favoriteService.removeFavorite(userId, productId, FavoriteTargetType.PRODUCT)) {
                            call.respond(HttpStatusCode.OK, "已取消收藏")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "未找到收藏记录")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "取消收藏失败: ${e.localizedMessage}")
                    }
                }
                // 获取商品的评论量、点赞量、收藏量
                get("/stats") {
                    val productId = call.parameters["productId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的产品ID")
                    try {
                        val stats = mapOf(
                            "likes" to likeService.countLikes(productId, LikeTargetType.PRODUCT),
                            "comments" to commentService.getCommentsByTargetId(productId, CommentTargetType.PRODUCT).size,
                            "favorites" to favoriteService.countFavoritesByTargetId(productId, FavoriteTargetType.PRODUCT)
                        )
                        call.respond(HttpStatusCode.OK, stats)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "获取统计信息失败: ${e.localizedMessage}")
                    }
                }
            }
        }
    }
}
