package com.example.routes.auth

import com.example.models.transmissionModels.auth.merchant.MerchantApplication
import com.example.models.transmissionModels.auth.merchant.MerchantApplicationRequest
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
import java.time.LocalDateTime
import java.util.*

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
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        mapOf("error" to "注册失败: ${e.localizedMessage}")
                    )
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
                        call.respond(
                            HttpStatusCode.BadRequest,
                            LoginResponse(
                                success = false,
                                token = null,
                                refreshToken = null,
                                errorMessage = "验证码无效或已过期"
                            )
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        LoginResponse(
                            success = false,
                            token = null,
                            refreshToken = null,
                            errorMessage = "登录失败: ${e.localizedMessage}"
                        )
                    )
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
                        call.respond(
                            HttpStatusCode.BadRequest,
                            LoginResponse(
                                success = false,
                                token = null,
                                refreshToken = null,
                                errorMessage = "验证码无效或已过期"
                            )
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        LoginResponse(
                            success = false,
                            token = null,
                            refreshToken = null,
                            errorMessage = "登录失败: ${e.localizedMessage}"
                        )
                    )
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
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        SendCodeResponse(
                            success = false,
                            message = "发送失败: ${e.localizedMessage}",
                            errorCode = "INTERNAL_SERVER_ERROR"
                        )
                    )
                }
            }


            // 使用邮箱地址或手机号作为用户名的常规登录方式
            post("/login") {
                try {
                    val loginRequest = call.receive<PasswordLoginRequest>()
                    val response = authService.loginWithPassword(loginRequest.identifier, loginRequest.password)
                    call.respond(response)
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        LoginResponse(
                            success = false,
                            token = null,
                            refreshToken = null,
                            errorMessage = "登录失败: ${e.localizedMessage}"
                        )
                    )
                }
            }


            // 重置密码功能，通常涉及发送重置链接到用户邮箱
            post("/password/change-password") {
                try {
                    val request = call.receive<ResetPasswordRequest>()

                    if (authService.verifyCode(request.identifier, request.code)) {
                        if (authService.resetPassword(request.identifier, request.newPassword)) {
                            call.respond(
                                HttpStatusCode.OK,
                                ResetPasswordResponse(success = true, message = "重置密码成功")
                            )
                        } else {
                            call.respond(
                                HttpStatusCode.InternalServerError,
                                ResetPasswordResponse(success = false, message = "重置密码失败")
                            )
                        }
                    } else {
                        call.respond(
                            HttpStatusCode.BadRequest,
                            ResetPasswordResponse(success = false, message = "验证码无效或已过期")
                        )
                    }
                } catch (e: Exception) {
                    call.respond(
                        HttpStatusCode.InternalServerError,
                        ResetPasswordResponse(
                            success = false,
                            message = "重置密码失败: ${e.localizedMessage}",
                            errorCode = "INTERNAL_SERVER_ERROR"
                        )
                    )
                }
            }

            post("/refresh-token") {
                try {
                    val refreshTokenRequest = call.receive<RefreshTokenRequest>() // 从请求中获取刷新令牌
                    val response = authService.refreshToken(refreshTokenRequest.refreshToken)
                    if (response.success) {
                        call.respond(HttpStatusCode.OK, response)
                    } else {
                        call.respond(HttpStatusCode.Unauthorized, response)
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "刷新令牌失败: ${e.localizedMessage}")
                }
            }



            authenticate {

                post("/apply-for-merchant") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized, "未认证的请求")
                        return@post
                    }

                    val userId = principal.payload.getClaim("userId").asString()
                    val request = call.receive<MerchantApplicationRequest>()

                    val application = MerchantApplication(
                        applicationId = UUID.randomUUID().toString(),
                        userId = userId,
                        companyName = request.companyName,
                        contactNumber = request.contactNumber,
                        address = request.address,
                        licenseUrl = request.licenseUrl,
                        applicationStatus = ApplicationStatus.PENDING,
                        createdAt = LocalDateTime.now()
                    )

                    try {
                        val result = authService.applyForMerchant(userId, application)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "商家申请提交成功，等待审核")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "商家申请提交失败")
                        }
                    } catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, e.message ?: "请求无效")
                    }
                }


                post("/approve-merchant") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.SUPER_ADMIN)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }

                    val userId = call.receive<String>()

                    try {
                        val result = authService.approveMerchantApplication(userId)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "商家申请审核通过")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "商家申请审核失败")
                        }
                    } catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, e.message ?: "请求无效")
                    }
                }


                // 用户登出
                post("/logout") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized, "未认证的请求")
                        return@post
                    }

                    val userId = principal.payload.getClaim("userId").asString()

                    try {
                        val result = authService.logout(userId)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "成功登出")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "登出失败")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "服务器错误: ${e.localizedMessage}")
                    }
                }

                post("/update-user-info") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized, "未认证的请求")
                        return@post
                    }

                    val userId = principal.payload.getClaim("userId").asString()
                    val updateRequest = call.receive<UserInfoUpdateRequest>()

                    try {
                        val result = authService.updateUserInfo(userId, updateRequest)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "用户信息更新成功")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "用户信息更新失败")
                        }
                    } catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, e.message ?: "请求无效")
                    }
                }


                post("/binding-phone") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@post
                    }

                    val userId = principal.payload.getClaim("userId").asString()
                    val bindPhoneRequest = call.receive<BindPhoneRequest>()

                    try {
                        val result = authService.bindPhone(userId, bindPhoneRequest.newPhone, bindPhoneRequest.oldPhoneVerificationCode)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "绑定手机号成功")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "绑定手机号失败")
                        }
                    } catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, e.message ?: "请求无效")
                    }
                }


                post("/binding-email") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null) {
                        call.respond(HttpStatusCode.Unauthorized)
                        return@post
                    }

                    val userId = principal.payload.getClaim("userId").asString()
                    val bindEmailRequest = call.receive<BindEmailRequest>()

                    try {
                        val result = authService.bindEmail(userId, bindEmailRequest.newEmail, bindEmailRequest.oldEmailVerificationCode)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "绑定邮箱成功")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "绑定邮箱失败")
                        }
                    } catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, e.message ?: "请求无效")
                    }
                }


                post("/add-account") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.SUPER_ADMIN)) {
                        call.respond(HttpStatusCode.Forbidden, "权限不足")
                        return@post
                    }

                    val addAccountRequest = call.receive<AddAccountRequest>()
                    val currentUserRole = Role.valueOf(principal.payload.getClaim("role").asString())

                    try {
                        val result = authService.addAccount(addAccountRequest, currentUserRole)
                        if (result) {
                            call.respond(HttpStatusCode.OK, "添加账户成功")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "添加账户失败")
                        }
                    } catch (e: IllegalArgumentException) {
                        call.respond(HttpStatusCode.BadRequest, e.message ?: "请求无效")
                    }
                }
            }
        }
    }
}