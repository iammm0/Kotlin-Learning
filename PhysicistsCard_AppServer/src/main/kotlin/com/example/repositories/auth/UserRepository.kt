package com.example.repositories.auth

import IUserRepository
import com.example.models.databaseTableModels.auth.authToken.AuthTokens
import com.example.models.databaseTableModels.auth.user.Users
import com.example.models.transmissionModels.auth.user.Role
import com.example.models.transmissionModels.auth.user.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository :  IUserRepository {
    // 返回 User 类型的 createUser 方法
    override fun createUser(user: User): User {
        val insertedUserId = transaction {
            addLogger(StdOutSqlLogger)
            Users.insert {
                it[userId] = user.userId
                it[username] = user.username
                it[email] = user.email
                it[phone] = user.phone
                it[passwordHash] = user.passwordHash
                it[avatarUrl] = user.avatarUrl
                it[bio] = user.bio
                it[registerDate] = user.registerDate
                it[isEmailVerified] = user.isEmailVerified
                it[isPhoneVerified] = user.isPhoneVerified
                it[role] = user.role
            } get Users.userId
        }
        return user.copy(userId = insertedUserId)
    }

    // 返回 Boolean 类型的 createUser 方法
    override fun createUserAndReturnUser(user: User): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Users.insert {
                it[userId] = user.userId
                it[username] = user.username
                it[email] = user.email
                it[phone] = user.phone
                it[passwordHash] = user.passwordHash
                it[avatarUrl] = user.avatarUrl
                it[bio] = user.bio
                it[registerDate] = user.registerDate
                it[isEmailVerified] = user.isEmailVerified
                it[isPhoneVerified] = user.isPhoneVerified
                it[role] = user.role
            }.insertedCount > 0
        }
    }

    override fun updateUserEmailOrPhone(userId: String, email: String?, phone: String?): Boolean {
        val updatedRows = transaction {
            Users.update({ Users.userId eq userId }) {
                addLogger(StdOutSqlLogger)

                if (email != null) it[Users.email] = email
                if (phone != null) it[Users.phone] = phone
            }
        }
        return updatedRows > 0
    }


    override fun findUserByEmailOrPhone(identifier: String): User? {
        return transaction {
            addLogger(StdOutSqlLogger)

            Users.selectAll().where { (Users.email eq identifier) or (Users.phone eq identifier) }
                .mapNotNull {
                    User(
                        userId = it[Users.userId],
                        username = it[Users.username],
                        email = it[Users.email],
                        phone = it[Users.phone],
                        passwordHash = it[Users.passwordHash],
                        avatarUrl = it[Users.avatarUrl],
                        bio = it[Users.bio],
                        registerDate = it[Users.registerDate],
                        isEmailVerified = it[Users.isEmailVerified],
                        isPhoneVerified = it[Users.isPhoneVerified],
                        role = it[Users.role]
                    )
                }.singleOrNull()
        }
    }


    override fun resetUserPassword(userId: String, newPasswordHash: String): Boolean {
        val updatedRows = transaction {
            addLogger(StdOutSqlLogger)

            Users.update({ Users.userId eq userId }) {
                it[passwordHash] = newPasswordHash
            }
        }
        return updatedRows > 0
    }

    override fun updateUserRole(userId: String, role: Role): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Users.update({ Users.userId eq userId }) {
                it[Users.role] = role
            } > 0
        }
    }

    override fun deleteTokensByUserId(userId: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            AuthTokens.deleteWhere { AuthTokens.userId eq userId } > 0
        }
    }

    override fun updateUserAvatar(userId: String, avatarUrl: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Users.update({ Users.userId eq userId }) {
                it[Users.avatarUrl] = avatarUrl
            } > 0
        }
    }

    override fun updateUserPhone(userId: String, phone: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Users.update({ Users.userId eq userId }) {
                it[Users.phone] = phone
            } > 0
        }
    }

    override fun updateUserEmail(userId: String, email: String): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Users.update({ Users.userId eq userId }) {
                it[Users.email] = email
            } > 0
        }
    }

    override fun checkDuplicateEmailOrPhone(email: String?, phone: String?): Boolean {
        return transaction {
            addLogger(StdOutSqlLogger)
            Users.selectAll().where { (Users.email eq email) or (Users.phone eq phone) }.count() > 0
        }
    }
}