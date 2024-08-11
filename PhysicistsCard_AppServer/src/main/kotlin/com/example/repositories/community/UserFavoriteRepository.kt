package com.example.repositories.community

import com.example.models.databaseTableModels.community.interaction.PostStats
import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import com.example.models.transmissionModels.community.interaction.UserFavorite
import com.example.models.databaseTableModels.community.interaction.favorite.UserFavorites
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class UserFavoriteRepository : IUserFavoriteRepository {
    override fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite? = transaction {
        val existingFavorite = findFavorite(userId, targetId, targetType)
        if (existingFavorite == null) {
            val favoriteId = UUID.randomUUID().toString()
            UserFavorites.insert {
                it[this.favoriteId] = favoriteId
                it[this.userId] = userId
                it[this.targetId] = targetId
                it[this.targetType] = targetType
                it[createdAt] = LocalDateTime.now()
            }

            if (targetType == FavoriteTargetType.POST) {
                PostStats.update({ PostStats.postId eq UUID.fromString(targetId) }) {
                    with(SqlExpressionBuilder) {
                        it.update(favoritesCount, favoritesCount + 1)
                    }
                }
            }

            UserFavorite(favoriteId, userId, targetId, targetType, LocalDateTime.now())
        } else {
            null
        }
    }

    override fun findFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite? = transaction {
        UserFavorites.selectAll().where {
            (UserFavorites.userId eq userId) and
                    (UserFavorites.targetId eq targetId) and
                    (UserFavorites.targetType eq targetType)
        }.mapNotNull { it.toUserFavorite() }
            .singleOrNull()
    }

    override fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean = transaction {
        val deleted = UserFavorites.deleteWhere {
            (UserFavorites.userId eq userId) and
                    (UserFavorites.targetId eq targetId) and
                    (UserFavorites.targetType eq targetType)
        } > 0

        if (deleted && targetType == FavoriteTargetType.POST) {
            PostStats.update({ PostStats.postId eq UUID.fromString(targetId) }) {
                with(SqlExpressionBuilder) {
                    it.update(favoritesCount, favoritesCount - 1)
                }
            }
        }

        deleted
    }

    override fun findFavoritesByUserId(userId: String): List<UserFavorite> = transaction {
        UserFavorites.selectAll().where { UserFavorites.userId eq userId }
            .map { it.toUserFavorite() }
    }

    override fun findFavoritesByTargetId(targetId: String, targetType: FavoriteTargetType): List<UserFavorite> {
        return transaction {
            UserFavorites.selectAll().where {
                (UserFavorites.targetId eq targetId) and
                        (UserFavorites.targetType eq targetType)
            }.map { rowToUserFavorite(it) }
        }
    }

    private fun rowToUserFavorite(row: ResultRow): UserFavorite {
        return UserFavorite(
            favoriteId = row[UserFavorites.favoriteId],
            userId = row[UserFavorites.userId],
            targetId = row[UserFavorites.targetId],
            targetType = row[UserFavorites.targetType],
            createdAt = row[UserFavorites.createdAt]
        )
    }

    private fun ResultRow.toUserFavorite(): UserFavorite =
        UserFavorite(
            favoriteId = this[UserFavorites.favoriteId],
            userId = this[UserFavorites.userId],
            targetId = this[UserFavorites.targetId],
            targetType = FavoriteTargetType.valueOf(this[UserFavorites.targetType].name),
            createdAt = this[UserFavorites.createdAt]
        )
}
