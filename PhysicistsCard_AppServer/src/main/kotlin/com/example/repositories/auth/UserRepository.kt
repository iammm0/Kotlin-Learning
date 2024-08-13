package com.example.repositories.auth

import IUserRepository
import com.example.models.databaseTableModels.auth.authToken.RefreshTokens
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
            RefreshTokens.deleteWhere { RefreshTokens.userId eq userId } > 0
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

            // 使用 selectAll() 并在后面添加条件筛选
            val query = Users.selectAll().apply {
                when {
                    email != null && phone != null -> adjustWhere { (Users.email eq email) or (Users.phone eq phone) }
                    email != null -> adjustWhere { Users.email eq email }
                    phone != null -> adjustWhere { Users.phone eq phone }
                    else -> adjustWhere { Op.FALSE } // 无意义的操作，因为 email 和 phone 都是 null
                }
            }

            query.count() > 0
        }
    }

    override fun findUserById(userId: String): User? {
        return transaction {
            Users.selectAll().where { Users.userId eq userId }
                .mapNotNull { it.toUser() }
                .singleOrNull()
        }
    }

    override fun updateUser(user: User): Boolean {
        return transaction {
            val updatedRows = Users.update({ Users.userId eq user.userId }) {
                it[username] = user.username
                it[email] = user.email
                it[phone] = user.phone
                it[avatarUrl] = user.avatarUrl
                it[bio] = user.bio
                it[isEmailVerified] = user.isEmailVerified
                it[isPhoneVerified] = user.isPhoneVerified
                it[role] = user.role
            }
            updatedRows > 0
        }
    }

    private fun ResultRow.toUser() = User(
        userId = this[Users.userId],
        username = this[Users.username],
        email = this[Users.email],
        phone = this[Users.phone],
        passwordHash = this[Users.passwordHash],
        avatarUrl = this[Users.avatarUrl],
        bio = this[Users.bio],
        registerDate = this[Users.registerDate],
        isEmailVerified = this[Users.isEmailVerified],
        isPhoneVerified = this[Users.isPhoneVerified],
        role = this[Users.role]
    )
}