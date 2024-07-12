package com.example.repositories.community

import IUserCommentRepository
import com.example.models.databaseTableModels.community.post.mPost.Posts.updatedAt
import com.example.models.transmissionModels.community.interaction.CommentTargetType
import com.example.models.transmissionModels.community.interaction.UserComment
import com.example.models.databaseTableModels.community.interaction.comment.UserComments
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime
import java.util.*

class UserCommentRepository : IUserCommentRepository {
    override fun add(item: UserComment): UserComment = transaction {
        val commentId = UUID.randomUUID().toString()  // 生成唯一标识符
        UserComments.insert {
            it[this.commentId] = commentId
            it[userId] = item.userId
            it[targetId] = item.targetId
            it[targetType] = item.targetType
            it[content] = item.content
            it[createdAt] = item.createdAt
            it[parentId] = item.parentId
        }
        item.copy(commentId = commentId)  // 返回包含新生成 commentId 的完整对象
    }

    override fun findById(id: String): UserComment? = transaction {
        UserComments.selectAll().where { UserComments.commentId eq id }
            .mapNotNull { it.toUserComment() }
            .singleOrNull()
    }

    override fun update(item: UserComment): UserComment = transaction {
        UserComments.update({ UserComments.commentId eq item.commentId }) {
            it[content] = item.content
            it[updatedAt] = LocalDateTime.now()  // 更新评论时间
        }
        findById(item.commentId) ?: throw IllegalStateException("Comment with ID ${item.commentId} not found")
    }

    override fun delete(id: String): Boolean = transaction {
        UserComments.deleteWhere { commentId eq id } > 0
    }

    override fun findAll(): List<UserComment> = transaction {
        UserComments.selectAll().map { it.toUserComment() }
    }

    override fun findByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment> = transaction {
        UserComments.selectAll()
            .where { (UserComments.targetId eq targetId) and (UserComments.targetType eq targetType) }
            .map { it.toUserComment() }
    }

    override fun findReplies(commentId: String): List<UserComment> = transaction {
        UserComments.selectAll().where { UserComments.parentId eq commentId }
            .map { it.toUserComment() }
    }

    private fun ResultRow.toUserComment(): UserComment =
        UserComment(
            commentId = this[UserComments.commentId],
            userId = this[UserComments.userId],
            targetId = this[UserComments.targetId],
            targetType = CommentTargetType.valueOf(
                this[UserComments.targetType].name
            ),
            content = this[UserComments.content],
            createdAt = this[UserComments.createdAt],
            parentId = this[UserComments.parentId]
        )
}
