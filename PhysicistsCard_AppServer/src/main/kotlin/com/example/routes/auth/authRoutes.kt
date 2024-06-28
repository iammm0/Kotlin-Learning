package com.example.routes.auth

import com.example.models.transmissionModels.auth.requests.*
import com.example.models.transmissionModels.auth.responses.LoginResponse
import com.example.models.transmissionModels.auth.responses.ResetPasswordResponse
import com.example.models.transmissionModels.auth.responses.SendCodeResponse
import com.example.services.dataAccessServices.auth.AuthService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.authRoutes() {
    val authService = AuthService()

    routing {
        route("/user") {
            // 使用邮箱或手机注册账户
            post("/register") {
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
            }

            // 使用邮箱代码登录（适用于密码遗忘且使用邮箱注册的用户）
            post("/email-code-login") {
                val loginRequest = call.receive<EmailCodeLoginRequest>()
                // 验证邮箱验证码是否有效
                if (authService.verifyCode(loginRequest.email, loginRequest.emailCode)) {
                    // 验证码验证通过，执行登录逻辑，这里简化为直接返回成功信息
                    // 实际情况下，你可能需要返回更多信息，如用户信息、JWT令牌等
                    authService.loginWithVerificationCode(loginRequest.email, loginRequest.emailCode)
                    call.respond(HttpStatusCode.OK, LoginResponse(success = true, token = "", errorMessage = null))
                } else {
                    // 验证码无效或已过期
                    call.respond(HttpStatusCode.BadRequest, LoginResponse(success = false, token = null, errorMessage = "验证码无效或已过期"))
                }
            }

            // 使用短信验证码登录（适用于密码遗忘且使用短信注册的用户）
            post("/phone-code-login") {
                val loginRequest = call.receive<PhoneCodeLoginRequest>()

                try {
                    if (authService.verifyCode(loginRequest.phone, loginRequest.phoneCode)) {
                        // 验证码验证通过，执行登录逻辑，这里简化为直接返回成功信息
                        // 实际情况下，你可能需要返回更多信息，如用户信息、JWT令牌等
                        authService.loginWithVerificationCode(loginRequest.phone, loginRequest.phoneCode)
                        call.respond(HttpStatusCode.OK, LoginResponse(success = true, token = "", errorMessage = null))
                    } else {
                        // 验证码无效或已过期
                        call.respond(HttpStatusCode.BadRequest, LoginResponse(success = false, token = null, errorMessage = "验证码无效或已过期"))
                    }
                } catch (e: Exception) {
                    // 处理异常
                    call.respond(HttpStatusCode.InternalServerError, LoginResponse(success = false, token = null, errorMessage = "内部服务器错误: ${e.localizedMessage}"))
                }
            }

            // 邮箱与手机号码共用的api接口
            post("/send-code") {
                val codeRequest = call.receive<SendCodeRequest>()
                val identifier = codeRequest.identifier

                try {
                    val response = authService.sendVerificationCode(identifier, codeRequest)
                    call.respond(HttpStatusCode.OK, response)
                } catch(e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, SendCodeResponse(success = false, message = "发送失败: ${e.localizedMessage}", errorCode = "INTERNAL_SERVER_ERROR"))
                }
            }


            // 使用邮箱地址或手机号作为用户名的常规登录方式
            post("/login") {
                val loginRequest = call.receive<PasswordLoginRequest>()
                try {
                    if (authService.loginWithPassword(loginRequest.identifier, loginRequest.password)) {
                        call.respond(HttpStatusCode.OK, LoginResponse(success = true, token = "", errorMessage = null))
                    }
                } catch (e: Exception) {
                    // 如果验证失败，返回错误响应
                    call.respond(HttpStatusCode.Unauthorized, LoginResponse(success = false, token = null, errorMessage = "登录失败，邮箱/手机号或密码不正确"))
                }
            }



            // 重置密码功能，通常涉及发送重置链接到用户邮箱
            post("/password/change-password") {
                val request = call.receive<ResetPasswordRequest>()

                try {
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

        }
    }
}
