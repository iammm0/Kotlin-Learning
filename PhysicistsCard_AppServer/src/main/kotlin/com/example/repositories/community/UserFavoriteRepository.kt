package com.example.repositories.community

import com.example.models.transmissionModels.community.FavoriteTargetType
import com.example.models.transmissionModels.community.UserFavorite
import com.example.models.databaseTableModels.community.interaction.favorite.UserFavorites
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class UserFavoriteRepository : IUserFavoriteRepository {
    override fun addFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): UserFavorite = transaction {
        val favoriteId = UUID.randomUUID().toString()  // 生成唯一标识符
        UserFavorites.insert {
            it[this.favoriteId] = favoriteId
            it[this.userId] = userId
            it[this.targetId] = targetId
            it[this.targetType] = targetType
            it[createdAt] = LocalDateTime.now()  // 设置收藏时间为当前时间
        }
        UserFavorite(
            favoriteId,
            userId,
            targetId,
            targetType,
            LocalDateTime.now()
        )
    }

    override fun removeFavorite(userId: String, targetId: String, targetType: FavoriteTargetType): Boolean = transaction {
        UserFavorites.deleteWhere {
            (UserFavorites.userId eq userId) and
                    (UserFavorites.targetId eq targetId) and
                    (UserFavorites.targetType eq targetType)
        } > 0  // 返回是否成功删除（删除行数 > 0）
    }

    override fun findFavoritesByUserId(userId: String): List<UserFavorite> = transaction {
        UserFavorites.selectAll().where { UserFavorites.userId eq userId }
            .map { it.toUserFavorite() }
    }

    private fun ResultRow.toUserFavorite(): UserFavorite =
        UserFavorite(
            favoriteId = this[UserFavorites.favoriteId],
            userId = this[UserFavorites.userId],
            targetId = this[UserFavorites.targetId],
            targetType = FavoriteTargetType.valueOf(
                this[UserFavorites.targetType].name
            ),
            createdAt = this[UserFavorites.createdAt]
        )
}
