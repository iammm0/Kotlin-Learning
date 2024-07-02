package com.example.repositories.auth

import com.example.models.databaseTableModels.auth.user.Users
import com.example.models.transmissionModels.auth.User
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository :  IUserRepository {
    override fun createUser(user: User): User {
        val insertedUserId = transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Users)

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
            } get Users.userId
        }
        return user.copy(userId = insertedUserId)
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

}