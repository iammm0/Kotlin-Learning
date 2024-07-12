package com.example.physicistscard.transmissionModels.auth.user

import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: String, // 用户唯一标识符
    val username: String, // 用户名
    val email: String?, //
    val phone: String?, //
    val passwordHash: String, // 用户密码的哈希值，出于安全考虑不直接存储密码
    val avatarUrl: String?, // 用户头像URL，增强社区交流的个性化
    val bio: String?, // 个人简介，增加社区交流的深度
    @Serializable(with = LocalDateTimeSerializer::class)
    val registerDate: LocalDateTime, // 注册时间
    val isEmailVerified: Boolean = false, // 邮箱是否验证，重要的安全和通信特性
    val isPhoneVerified: Boolean = false, // 手机号是否验证，同样重要的安全和通信特性
    val role: Role // 新增角色字段
)