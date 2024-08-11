package com.example.repositories.community

import com.example.models.databaseTableModels.community.interaction.PostStats
import com.example.models.transmissionModels.community.post.PostStat
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PostStatsRepository : IPostStatsRepository {

    override fun findByPostId(postId: UUID): PostStat? = transaction {
        PostStats.selectAll().where { PostStats.postId eq postId }
            .mapNotNull { it.toPostStat() }
            .singleOrNull()
    }

    override fun createOrUpdate(postStat: PostStat): PostStat = transaction {
        val existingStat = PostStats.selectAll().where { PostStats.postId eq postStat.postId }
            .singleOrNull()

        if (existingStat == null) {
            // 如果统计信息不存在，插入新数据
            PostStats.insert {
                it[PostStats.postId] = postStat.postId
                it[likesCount] = postStat.likesCount
                it[commentsCount] = postStat.commentsCount
                it[favoritesCount] = postStat.favoritesCount
            }
        } else {
            // 如果统计信息存在，更新现有数据
            PostStats.update({ PostStats.postId eq postStat.postId }) {
                it[likesCount] = postStat.likesCount
                it[commentsCount] = postStat.commentsCount
                it[favoritesCount] = postStat.favoritesCount
            }
        }

        findByPostId(postStat.postId) ?: throw IllegalStateException("Failed to create or update PostStat")
    }

    override fun update(postStat: PostStat): PostStat = transaction {
        PostStats.update({ PostStats.postId eq postStat.postId }) {
            it[likesCount] = postStat.likesCount
            it[commentsCount] = postStat.commentsCount
            it[favoritesCount] = postStat.favoritesCount
        }

        findByPostId(postStat.postId) ?: throw IllegalStateException("PostStat with ID ${postStat.postId} not found")
    }

    override fun delete(postId: UUID): Boolean = transaction {
        PostStats.deleteWhere { PostStats.postId eq postId } > 0
    }

    // 私有方法，用于将数据库表行转换为传输模型 PostStat
    private fun ResultRow.toPostStat(): PostStat = PostStat(
        postId = this[PostStats.postId].value,
        likesCount = this[PostStats.likesCount],
        commentsCount = this[PostStats.commentsCount],
        favoritesCount = this[PostStats.favoritesCount]
    )
}
