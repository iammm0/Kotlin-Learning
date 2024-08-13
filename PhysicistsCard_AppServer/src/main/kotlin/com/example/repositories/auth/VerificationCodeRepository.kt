package com.example.repositories.auth

import com.example.models.databaseTableModels.auth.verification.VerificationCodes
import com.example.models.transmissionModels.auth.verificationCodes.VerificationType
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId


class VerificationCodeRepository : IVerificationCodeRepository {

    override fun saveVerificationCode(
        code: String,
        identifier: String,
        validityDuration: Long,
        type: VerificationType
    ): Boolean {
        val createTime = LocalDateTime.now()
        val expiryTime = createTime.plusMinutes(validityDuration)
        val expiryTimeInstant = expiryTime.atZone(ZoneId.systemDefault()).toInstant()

        return try {
            transaction {
                addLogger(StdOutSqlLogger)

                VerificationCodes.insert {
                    it[this.code] = code
                    it[target] = identifier
                    it[this.expiryDate] = expiryTimeInstant
                    it[this.type] = type
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun verifyCode(identifier: String, code: String): Boolean {
        val now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()

        return transaction {
            addLogger(StdOutSqlLogger)

            val codeQuery = VerificationCodes.selectAll().where {
                (VerificationCodes.code eq code) and
                        (VerificationCodes.target eq identifier) and
                        (VerificationCodes.expiryDate greaterEq now) and
                        (VerificationCodes.isUsed eq false)
            }
            // 如果找到记录，且未被使用，则标记为已使用
            // 检查是否有符合条件的记录
            if (codeQuery.count() > 0) {
                val codeRecord = codeQuery.single()
                val codeId = codeRecord[VerificationCodes.id].value // 使用 .value 获取 IntIdTable 的主键值
                val markedAsUsed = markCodeAsUsed(codeId)
                markedAsUsed // 返回 true 表示验证成功，并标记为已使用
            } else {
                false
            }
        }
    }

    // 实现根据 codeId 标记验证码为已使用
    override fun markCodeAsUsed(codeId: Int): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            val updatedRows = VerificationCodes.update({ VerificationCodes.id eq codeId }) {
                it[isUsed] = true
            }
            updatedRows > 0
        }
    }

    override fun verifyAndMarkCodeAsUsed(identifier: String, code: String): Boolean {
        val now = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()

        return transaction {
            addLogger(StdOutSqlLogger)

            val codeQuery = VerificationCodes.select {
                (VerificationCodes.code eq code) and
                        (VerificationCodes.target eq identifier) and
                        (VerificationCodes.expiryDate greaterEq now) and
                        (VerificationCodes.isUsed eq false)
            }

            if (codeQuery.count() > 0) {
                val codeId = codeQuery.single()[VerificationCodes.id].value
                val updatedRows = VerificationCodes.update({ VerificationCodes.id eq codeId }) {
                    it[isUsed] = true
                }
                updatedRows > 0 // 返回 true 表示验证成功，并标记为已使用
            } else {
                false
            }
        }
    }

    // 实现根据 identifier 和 code 标记验证码为已使用
    override fun markCodeAsUsed(identifier: String, code: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            val codeId = VerificationCodes
                .selectAll().where { (VerificationCodes.target eq identifier) and (VerificationCodes.code eq code) }
                .singleOrNull()?.get(VerificationCodes.id)?.value ?: return@transaction false

            markCodeAsUsed(codeId)
        }
    }


    override fun deleteExpiredVerificationCodes(): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            VerificationCodes.deleteWhere {
                (expiryDate lessEq Instant.now()) or (isUsed eq true)
            } > 0 // 删除操作返回删除的行数，如果大于0则表示成功删除了记录
        }
    }
}


