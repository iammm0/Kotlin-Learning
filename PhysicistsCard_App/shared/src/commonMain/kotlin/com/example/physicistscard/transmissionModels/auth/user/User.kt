package com.example.physicistscard.transmissionModels.auth.user

import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: String, // 用户唯一标识符
    val username: String, // 用户名（唯一）
    val displayName: String?, // 用户在聊天系统中的显示昵称，可以与用户名不同
    val email: String?, // 用户邮箱
    val phone: String?, // 用户电话
    val passwordHash: String, // 用户密码的哈希值
    val avatarUrl: String?, // 用户头像URL
    val bio: String?, // 个人简介
    val isOnline: Boolean = false, // 用户当前在线状态
    val lastActive: LocalDateTime?, // 用户最后一次活动时间
    @Serializable(with = LocalDateTimeSerializer::class)
    val registerDate: LocalDateTime, // 注册时间
    val isEmailVerified: Boolean = false, // 邮箱是否验证
    val isPhoneVerified: Boolean = false, // 手机号是否验证
    val role: Role, // 用户角色
    val friendList: List<String> = emptyList() // 好友列表，存储好友的userId
)
