package com.example

// 集成支付与物流服务
// 配置 JWT Web 认证令牌
// 配置 WebSocket 实时通信
// 重新检查数据库表结构
// 配置数据库连接池与缓存
// 编写单元测试与集成测试

import IAuthTokenRepository
import IUserCommentRepository
import IUserRepository
import com.example.configs.configurations
import com.example.repositories.auth.IVerificationCodeRepository
import com.example.repositories.auth.TokenRepository
import com.example.repositories.auth.UserRepository
import com.example.services.TokenCleanupService
import com.example.routes.auth.authRoutes
import com.example.routes.community.communityRoutes
import com.example.routes.store.storeRoutes
import com.example.services.VerificationCodesCleanupService
import com.example.repositories.auth.VerificationCodeRepository
import com.example.repositories.community.*
import com.example.repositories.store.*
import com.example.services.dataAccessServices.auth.AuthService
import com.example.services.dataAccessServices.auth.IAuthService
import com.example.services.dataAccessServices.auth.ITokenService
import com.example.services.dataAccessServices.auth.JwtITokenService
import com.example.services.dataAccessServices.community.*
import com.example.services.dataAccessServices.store.IOrderService
import com.example.services.dataAccessServices.store.IProductService
import com.example.services.dataAccessServices.store.OrderService
import com.example.services.dataAccessServices.store.ProductService
import com.example.utils.DatabaseConfig
import com.example.utils.DatabaseTables
import com.typesafe.config.ConfigFactory
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

    // 加载配置
    val config = ConfigFactory.load()
    val jwtSecret = config.getString("ktor.security.jwt.secret")
    val jwtIssuer = config.getString("ktor.security.jwt.issuer")
    val jwtValidityInMs = config.getLong("ktor.security.jwt.validityInMs")

    val userRepository: IUserRepository = UserRepository()
    val postRepository: IPostRepository = PostRepository()
    val verificationCodeRepository: IVerificationCodeRepository = VerificationCodeRepository()
    val userCommentRepository: IUserCommentRepository = UserCommentRepository()
    val userLikeRepository: IUserLikeRepository = UserLikeRepository()
    val productRepository: IProductRepository = ProductRepository()
    val paymentInfoRepository: IPaymentInfoRepository = PaymentInfoRepository()
    val shippingInfoRepository: IShippingInfoRepository  = ShippingInfoRepository()
    val orderRepository: IOrderRepository = OrderRepository(paymentInfoRepository, shippingInfoRepository)
    val tokenRepository: IAuthTokenRepository = TokenRepository()

    val verificationCodesCleanupService = VerificationCodesCleanupService(verificationCodeRepository)
    val postService: IPostService = PostService(postRepository)
    val commentService: IUserCommentService = UserCommentService(userCommentRepository)
    val likeService: IUserLikeService = UserLikeService(userLikeRepository)
    val favoriteService: IUserFavoriteService = UserFavoriteService()
    val productService: IProductService = ProductService(productRepository)
    val orderService: IOrderService = OrderService(orderRepository)
    val tokenService: ITokenService = JwtITokenService(jwtSecret, jwtIssuer, jwtValidityInMs)
    val tokenCleanupService = TokenCleanupService(tokenRepository)
    val authService: IAuthService = AuthService(tokenService, userRepository, verificationCodeRepository, tokenRepository)

    // 启动应用...
    verificationCodesCleanupService.startCleanupJob()
    tokenCleanupService.startCleanupJob()

    authRoutes(authService)
    communityRoutes(
        postService,
        commentService,
        likeService,
        favoriteService
    )
    storeRoutes(
        productService,
        orderService,
        commentService,
        likeService,
        favoriteService
    )
    // 应用关闭前
    verificationCodesCleanupService.stopCleanupJob()
}
