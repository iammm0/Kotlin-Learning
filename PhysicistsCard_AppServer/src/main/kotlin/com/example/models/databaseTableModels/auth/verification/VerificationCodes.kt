package com.example.models.databaseTableModels.auth.verification

import com.example.models.transmissionModels.auth.verificationCodes.VerificationType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.timestamp

// 假设每个验证码都是唯一的，我们可以使用自增的主键
object VerificationCodes : IntIdTable() {
    val code = varchar("code", 255)
    val target = varchar("target", 255)
    val expiryDate = timestamp("expiryDate") // 使用timestamp存储Instant类型
    val type = enumerationByName("type", 50, VerificationType::class)
    val isUsed = bool("isUsed").default(false) // 假设添加了这个字段
}