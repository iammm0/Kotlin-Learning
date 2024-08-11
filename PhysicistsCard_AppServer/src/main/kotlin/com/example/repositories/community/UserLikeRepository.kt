package com.example.repositories.community

import com.example.models.databaseTableModels.community.interaction.PostStats
import com.example.models.transmissionModels.community.interaction.LikeTargetType
import com.example.models.transmissionModels.community.interaction.UserLike
import com.example.models.databaseTableModels.community.interaction.like.UserLikes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class UserLikeRepository : IUserLikeRepository {
    override fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike? = transaction {
        val existingLike = findLike(userId, targetId, targetType)
        if (existingLike == null) {
            val likeId = UUID.randomUUID().toString()
            UserLikes.insert {
                it[this.likeId] = likeId
                it[this.userId] = userId
                it[this.targetId] = targetId
                it[this.targetType] = targetType
                it[createdAt] = LocalDateTime.now()
            }

            if (targetType == LikeTargetType.POST) {
                PostStats.update({ PostStats.postId eq UUID.fromString(targetId) }) {
                    with(SqlExpressionBuilder) {
                        it.update(likesCount, likesCount + 1)
                    }
                }
            }

            UserLike(likeId, userId, targetId, targetType, LocalDateTime.now())
        } else {
            null
        }
    }

    override fun findLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike? = transaction {
        UserLikes.selectAll().where {
            (UserLikes.userId eq userId) and
                    (UserLikes.targetId eq targetId) and
                    (UserLikes.targetType eq targetType)
        }.mapNotNull { it.toUserLike() }
            .singleOrNull()
    }

    override fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean = transaction {
        val deleted = UserLikes.deleteWhere {
            (UserLikes.userId eq userId) and
                    (UserLikes.targetId eq targetId) and
                    (UserLikes.targetType eq targetType)
        } > 0

        if (deleted && targetType == LikeTargetType.POST) {
            PostStats.update({ PostStats.postId eq UUID.fromString(targetId) }) {
                with(SqlExpressionBuilder) {
                    it.update(likesCount, likesCount - 1)
                }
            }
        }

        deleted
    }

    override fun findLikesByUserId(userId: String): List<UserLike> = transaction {
        UserLikes.selectAll().where { UserLikes.userId eq userId }
            .map { it.toUserLike() }
    }

    override fun countLikes(targetId: String, targetType: LikeTargetType): Int = transaction {
        UserLikes.selectAll().where {
            (UserLikes.targetId eq targetId) and
                    (UserLikes.targetType eq targetType)
        }.count().toInt()  // 计算并返回总数
    }

    private fun ResultRow.toUserLike(): UserLike =
        UserLike(
            likeId = this[UserLikes.likeId],
            userId = this[UserLikes.userId],
            targetId = this[UserLikes.targetId],
            targetType = LikeTargetType.valueOf(this[UserLikes.targetType].name),
            createdAt = this[UserLikes.createdAt]
        )
}
