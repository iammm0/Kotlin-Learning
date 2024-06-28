package com.example.repositories.community

import com.example.models.databaseTableModels.community.post.content.*
import com.example.models.databaseTableModels.community.post.mPost.*
import com.example.models.transmissionModels.community.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class PostRepository : IPostRepository {
    override fun findByAuthorId(userId: String): List<Post> = transaction {
        com.example.models.databaseTableModels.community.post.mPost.Posts.selectAll().where { com.example.models.databaseTableModels.community.post.mPost.Posts.userId eq userId }
            .map { it.toPost() }
    }

    override fun findByCategory(category: String): List<Post> = transaction {
        com.example.models.databaseTableModels.community.post.mPost.Posts.selectAll().where { com.example.models.databaseTableModels.community.post.mPost.Posts.category eq category }
            .map { it.toPost() }
    }

    override fun addTag(postId: String, tag: String): Boolean = transaction {
        val tagId = com.example.models.databaseTableModels.community.post.mPost.PostTags.selectAll().where { com.example.models.databaseTableModels.community.post.mPost.PostTags.name eq tag }
            .singleOrNull()?.get(com.example.models.databaseTableModels.community.post.mPost.PostTags.id)?.value ?: com.example.models.databaseTableModels.community.post.mPost.PostTags.insertAndGetId {
            it[name] = tag
        }.value

        val exists = com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.selectAll()
            .where { (com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.postId eq postId.toInt()) and (com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.tagId eq tagId) }.any()

        if (!exists) {
            com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.insert {
                it[com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.postId] = postId.toInt()  // 注意转换类型
                it[com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.tagId] = tagId
            }
            true
        } else {
            false
        }
    }

    override fun removeTag(postId: String, tag: String): Boolean = transaction {
        val intPostId = postId.toInt()  // 将 String 类型的 postId 转换为 Int

        // val tagId = PostTags.selectAll().where { PostTags.name eq tag }
        //     .singleOrNull()?.get(PostTags.id)?.value

        val tagId = com.example.models.databaseTableModels.community.post.mPost.PostTags.select(com.example.models.databaseTableModels.community.post.mPost.PostTags.id).where { com.example.models.databaseTableModels.community.post.mPost.PostTags.name eq tag }
            .singleOrNull()?.get(com.example.models.databaseTableModels.community.post.mPost.PostTags.id)?.value

        // if (tagId != null) {
        //     PostTagRelations.deleteWhere {
        //         (PostTagRelations.postId eq postId.toInt()) and (PostTagRelations.tagId eq tagId)
        //     } > 0  // 删除成功则返回 true，否则返回 false
        // } else {
        //     false  // 标签不存在则返回 false
        // }

        if (tagId != null) {
            com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.deleteWhere {
                (com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.postId eq intPostId) and (com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.tagId eq tagId)
            } > 0  // 返回 Boolean 类型，表示是否成功删除
        } else {
            false
        }
    }

    override fun findByTag(tag: String): List<Post> = transaction {
        (com.example.models.databaseTableModels.community.post.mPost.PostTags innerJoin com.example.models.databaseTableModels.community.post.mPost.PostTagRelations innerJoin com.example.models.databaseTableModels.community.post.mPost.Posts)
            .selectAll().where { com.example.models.databaseTableModels.community.post.mPost.PostTags.name eq tag }
            .map { it.toPost() }
            .distinctBy { it.postId }
    }

    override fun add(item: Post): Post = transaction {
        val postId = com.example.models.databaseTableModels.community.post.mPost.Posts.insert {
            it[userId] = item.userId
            it[title] = item.title
            it[createdAt] = item.createdAt
            it[updatedAt] = item.updatedAt
            it[category] = item.category
        } get com.example.models.databaseTableModels.community.post.mPost.Posts.id

        findById(postId.value.toString()) ?: throw RuntimeException("未能成功插入推送条目 $postId")
    }

    override fun findById(id: String): Post? = transaction {
        com.example.models.databaseTableModels.community.post.mPost.Posts.selectAll().where { com.example.models.databaseTableModels.community.post.mPost.Posts.id eq id.toInt() }
            .mapNotNull { it.toPost() }
            .singleOrNull()
    }

    override fun findAll(): List<Post?> = transaction {
        com.example.models.databaseTableModels.community.post.mPost.Posts.selectAll()
            .map { it.toPost() }
    }

    override fun update(item: Post): Post = transaction {
        com.example.models.databaseTableModels.community.post.mPost.Posts.update({ com.example.models.databaseTableModels.community.post.mPost.Posts.id eq item.postId.toInt() }) {
            it[title] = item.title
            it[updatedAt] = LocalDateTime.now()
            it[category] = item.category
        }
        findById(item.postId) ?: throw RuntimeException("Post with ID ${item.postId} not found")
    }

    override fun delete(id: String): Boolean = transaction {
        com.example.models.databaseTableModels.community.post.mPost.Posts.deleteWhere { com.example.models.databaseTableModels.community.post.mPost.Posts.id eq id.toInt() } > 0
    }

    private fun ResultRow.toPost(): Post =
        Post(
            postId = this[Posts.id].value.toString(),
            userId = this[Posts.userId],
            title = this[Posts.title],
            createdAt = this[Posts.createdAt],
            updatedAt = this[Posts.updatedAt],
            category = this[Posts.category],
            tags = getPostTags(this[Posts.id].value.toString()),  // 获取帖子的所有标签
            contents = getPostContents(this[Posts.id].value.toString())  // 获取帖子的所有内容
        )

    private fun getPostTags(postId: String): List<String> = transaction {
        com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.join(com.example.models.databaseTableModels.community.post.mPost.PostTags, JoinType.INNER, additionalConstraint = { com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.tagId eq com.example.models.databaseTableModels.community.post.mPost.PostTags.id })
            .selectAll().where { com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.postId eq postId.toInt() }
            .map { it[com.example.models.databaseTableModels.community.post.mPost.PostTags.name] }
    }
    // 在数据库层面获取推送内容详情
    private fun getPostContents(postId: String): List<Content> = transaction {
        val intPostId = postId.toInt()  // 将 postId 字符串转为整数
        val contents = mutableListOf<Content>() // 在推送内容组成方式上采用可变列表
        val contentEntries = com.example.models.databaseTableModels.community.post.content.Contents.selectAll().where { com.example.models.databaseTableModels.community.post.content.Contents.postId eq intPostId }.toList()

        contentEntries.forEach { row ->
            when (row[com.example.models.databaseTableModels.community.post.content.Contents.type]) {
                ContentType.TextContent -> {
                    val textContent = com.example.models.databaseTableModels.community.post.content.TextContents.selectAll()
                        .where { com.example.models.databaseTableModels.community.post.content.TextContents.contentId eq row[com.example.models.databaseTableModels.community.post.content.Contents.contentId] }
                        .firstOrNull()?.let {
                            TextContent(
                                contentId = row[Contents.contentId],
                                order = row[Contents.order],
                                text = it[TextContents.text]
                            )
                        }
                    if (textContent != null) {
                        contents.add(textContent)
                    }
                }
                ContentType.ImageContent -> {
                    val imageContent = com.example.models.databaseTableModels.community.post.content.ImageContents.selectAll()
                        .where { com.example.models.databaseTableModels.community.post.content.ImageContents.contentId eq row[com.example.models.databaseTableModels.community.post.content.Contents.contentId] }
                        .firstOrNull()?.let {
                            ImageContent(
                                contentId = row[Contents.contentId],
                                order = row[Contents.order],
                                imageUrl = it[ImageContents.imageUrl]
                            )
                        }
                    if (imageContent != null) {
                        contents.add(imageContent)
                    }
                }
                ContentType.VideoContent -> {
                    val videoContent = com.example.models.databaseTableModels.community.post.content.VideoContents.selectAll()
                        .where { com.example.models.databaseTableModels.community.post.content.VideoContents.contentId eq row[com.example.models.databaseTableModels.community.post.content.Contents.contentId] }
                        .firstOrNull()?.let {
                            VideoContent(
                                contentId = row[Contents.contentId],
                                order = row[Contents.order],
                                videoUrl = it[VideoContents.videoUrl]
                            )
                        }
                    if (videoContent != null) {
                        contents.add(videoContent)
                    }
                }
            }
        }
        contents.sortBy { it.order }
        contents
    }
}
