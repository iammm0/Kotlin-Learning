package com.example.repositories.community

import com.example.models.transmissionModels.community.LikeTargetType
import com.example.models.transmissionModels.community.UserLike
import com.example.models.databaseTableModels.community.interaction.like.UserLikes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class UserLikeRepository : IUserLikeRepository {
    override fun addLike(userId: String, targetId: String, targetType: LikeTargetType): UserLike = transaction {
        val likeId = UUID.randomUUID().toString()  // 生成唯一标识符
        com.example.models.databaseTableModels.community.interaction.like.UserLikes.insert {
            it[this.likeId] = likeId
            it[this.userId] = userId
            it[this.targetId] = targetId
            it[this.targetType] = targetType
            it[createdAt] = LocalDateTime.now()  // 设置点赞时间为当前时间
        }
        UserLike(
            likeId,
            userId,
            targetId,
            targetType,
            LocalDateTime.now()
        )
    }

    override fun removeLike(userId: String, targetId: String, targetType: LikeTargetType): Boolean = transaction {
        UserLikes.deleteWhere {
            (UserLikes.userId eq userId) and
                    (UserLikes.targetId eq targetId) and
                    (UserLikes.targetType eq targetType)
        } > 0  // 返回是否成功删除（删除行数 > 0）
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
