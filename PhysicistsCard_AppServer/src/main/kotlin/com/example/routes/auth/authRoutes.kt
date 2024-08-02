package com.example.routes.auth

import com.example.models.transmissionModels.auth.requests.*
import com.example.models.transmissionModels.auth.responses.LoginResponse
import com.example.models.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.models.transmissionModels.auth.responses.SendCodeResponse
import com.example.models.transmissionModels.auth.user.Role
import com.example.services.dataAccessServices.auth.IAuthService
import com.example.utils.hasRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoutes(
    authService: IAuthService
) {

    routing {
        route("/user") {
            // **使用邮箱或手机注册账户**
            post("/register") {
                try {
                    val regisRequest = call.receive<RegistrationRequest>()

                    val identifier = regisRequest.email ?: regisRequest.phone
                    ?: return@post call.respond(HttpStatusCode.BadRequest, mapOf("error" to "需要提供邮箱或手机号"))

                    // 首先验证验证码是否有效
                    if (!authService.verifyCode(identifier, regisRequest.code)) {
                        call.respond(HttpStatusCode.BadRequest, mapOf("error" to "验证码无效或已过期"))
                        return@post
                    }

                    // 验证码有效，调用创建用户的服务模块
                    val regisResponse = authService.registerUser(regisRequest)
                    call.respond(HttpStatusCode.Created, regisResponse)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "注册失败: ${e.localizedMessage}"))
                }
            }

            // 使用邮箱代码登录（适用于密码遗忘且使用邮箱注册的用户）
            post("/email-code-login") {
                try {
                    val loginRequest = call.receive<EmailCodeLoginRequest>()
                    // 验证邮箱验证码是否有效
                    if (authService.verifyCode(loginRequest.email, loginRequest.emailCode)) {
                        val response = authService.loginWithVerificationCode(loginRequest.email, loginRequest.emailCode)
                        call.respond(response)
                    } else {
                        call.respond(HttpStatusCode.BadRequest, LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "验证码无效或已过期"))
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "登录失败: ${e.localizedMessage}"))
                }
            }


            // 使用短信验证码登录（适用于密码遗忘且使用短信注册的用户）
            post("/phone-code-login") {
                try {
                    val loginRequest = call.receive<PhoneCodeLoginRequest>()

                    if (authService.verifyCode(loginRequest.phone, loginRequest.phoneCode)) {
                        val response = authService.loginWithVerificationCode(loginRequest.phone, loginRequest.phoneCode)
                        call.respond(response)
                    } else {
                        call.respond(HttpStatusCode.BadRequest, LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "验证码无效或已过期"))
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "登录失败: ${e.localizedMessage}"))
                }
            }

            // 邮箱与手机号码共用的api接口
            post("/send-code") {
                try {
                    val codeRequest = call.receive<SendCodeRequest>()
                    val identifier = codeRequest.identifier

                    val response = authService.sendVerificationCode(identifier, codeRequest)
                    call.respond(HttpStatusCode.OK, response)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, SendCodeResponse(success = false, message = "发送失败: ${e.localizedMessage}", errorCode = "INTERNAL_SERVER_ERROR"))
                }
            }


            // 使用邮箱地址或手机号作为用户名的常规登录方式
            post("/login") {
                try {
                    val loginRequest = call.receive<PasswordLoginRequest>()
                    val response = authService.loginWithPassword(loginRequest.identifier, loginRequest.password)
                    call.respond(response)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, LoginResponse(success = false, token = null, refreshToken = null, errorMessage = "登录失败: ${e.localizedMessage}"))
                }
            }



            // 重置密码功能，通常涉及发送重置链接到用户邮箱
            post("/password/change-password") {
                try {
                    val request = call.receive<ResetPasswordRequest>()

                    if (authService.verifyCode(request.identifier, request.code)) {
                        if (authService.resetPassword(request.identifier, request.newPassword)) {
                            call.respond(HttpStatusCode.OK, ResetPasswordResponse(success = true, message = "重置密码成功"))
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, ResetPasswordResponse(success = false, message = "重置密码失败"))
                        }
                    } else {
                        call.respond(HttpStatusCode.BadRequest, ResetPasswordResponse(success = false, message = "验证码无效或已过期"))
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, ResetPasswordResponse(success = false, message = "重置密码失败: ${e.localizedMessage}", errorCode = "INTERNAL_SERVER_ERROR"))
                }
            }

            authenticate {
                // 注册为商家
                post("/become_seller") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }
                    val userId = principal.payload.getClaim("userId").asString()
                    if (authService.becomeSeller(userId)) {
                        call.respond(HttpStatusCode.OK, "注册为商家成功")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "注册为商家失败")
                    }
                }

                // 用户登出
                post("/logout") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@post
                    }
                    val userId = principal.payload.getClaim("userId").asString()
                    if (authService.logout(userId)) {
                        call.respond(HttpStatusCode.OK, "登出成功")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "登出失败")
                    }
                }

                // 切换头像
                post("/change-avatar") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@post
                    }
                    val userId = principal.payload.getClaim("userId").asString()
                    val avatarUrl = call.receive<String>()
                    if (authService.changeAvatar(userId, avatarUrl)) {
                        call.respond(HttpStatusCode.OK, "头像切换成功")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "头像切换失败")
                    }
                }

                // 绑定手机
                post("/binding-phone") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@post
                    }
                    val userId = principal.payload.getClaim("userId").asString()
                    val phone = call.receive<String>()
                    if (authService.bindPhone(userId, phone)) {
                        call.respond(HttpStatusCode.OK, "绑定手机成功")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "绑定手机失败")
                    }
                }

                // 绑定邮箱
                post("/binding-email") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@post
                    }
                    val userId = principal.payload.getClaim("userId").asString()
                    val email = call.receive<String>()
                    if (authService.bindEmail(userId, email)) {
                        call.respond(HttpStatusCode.OK, "绑定邮箱成功")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "绑定邮箱失败")
                    }
                }

                // 添加账户
                post("/add-account") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.SUPER_ADMIN)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }
                    val addAccountRequest = call.receive<AddAccountRequest>()
                    if (authService.addAccount(addAccountRequest)) {
                        call.respond(HttpStatusCode.OK, "添加账户成功")
                    } else {
                        call.respond(HttpStatusCode.InternalServerError, "添加账户失败")
                    }
                }
            }
        }
    }
}