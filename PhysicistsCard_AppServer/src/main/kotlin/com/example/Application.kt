package com.example

// 集成支付与物流服务
// 配置 JWT Web 认证令牌
// 配置 WebSocket 实时通信
// 重新检查数据库表结构
// 配置数据库连接池与缓存
// 编写单元测试与集成测试

import com.example.configs.configurations
import com.example.routes.auth.authRoutes
import com.example.routes.community.communityRoutes
import com.example.routes.store.storeRoutes
import com.example.repositories.auth.CleanupService
import com.example.repositories.auth.VerificationCodeRepository
import com.example.repositories.community.PostRepository
import com.example.repositories.community.UserCommentRepository
import com.example.repositories.community.UserFavoriteRepository
import com.example.repositories.community.UserLikeRepository
import com.example.repositories.store.OrderRepository
import com.example.repositories.store.ProductRepository
import com.example.services.dataAccessServices.community.PostService
import com.example.services.dataAccessServices.community.UserCommentService
import com.example.services.dataAccessServices.community.UserFavoriteService
import com.example.services.dataAccessServices.community.UserLikeService
import com.example.services.dataAccessServices.store.OrderService
import com.example.services.dataAccessServices.store.ProductService
import com.example.utils.DatabaseConfig
import com.example.utils.DatabaseTables
import io.ktor.server.application.*
// 处理请求时需要注意执行的一些操作：
// 一.请求信息：
// ApplicationRequest.headers的acceptEncoding属性可以使其访问所有请求标头。
// 专用扩展函数可访问特定标头：例如，contentType,cacheControl等。
// ApplicationRequest.cookies属性提供对与请求相关的cookies的访问。
// ApplicationRequest.local属性提供连接详细信息，例如主机名，端口，方案等。
// 二.路径参数：
// 三.查询参数：
// 四.正文内容
fun main(args: Array<String>) { io.ktor.server.netty.EngineMain.main(args) }

fun Application.module() {

    DatabaseConfig.init()
    DatabaseTables.init()

    configurations()

    val postRepository = PostRepository()
    val verificationCodeRepository = VerificationCodeRepository()
    val userCommentRepository = UserCommentRepository()
    val userLikeRepository = UserLikeRepository()
    val userFavoriteRepository = UserFavoriteRepository()
    val productRepository = ProductRepository()
    val orderRepository = OrderRepository()

    val cleanupService = CleanupService(verificationCodeRepository)
    val postService = PostService(postRepository)
    val commentService = UserCommentService(userCommentRepository)
    val likeService = UserLikeService(userLikeRepository)
    val favoriteRepository = UserFavoriteService(userFavoriteRepository)
    val productService = ProductService(productRepository)
    val orderService = OrderService(orderRepository)

    // 启动应用...
    cleanupService.startCleanupJob()

    authRoutes()
    communityRoutes(
        postService,
        commentService,
        likeService,
        favoriteRepository
    )
    storeRoutes(
        productService,
        orderService,
        commentService,
        likeService,
        favoriteRepository
    )
    // 应用关闭前
    cleanupService.stopCleanupJob()
}
