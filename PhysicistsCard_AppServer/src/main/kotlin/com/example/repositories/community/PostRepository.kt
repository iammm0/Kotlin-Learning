package com.example.repositories.community

import com.example.models.databaseTableModels.community.post.content.*
import com.example.models.databaseTableModels.community.post.mPost.*
import com.example.models.transmissionModels.community.post.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class PostRepository : IPostRepository {
    override fun findByAuthorId(userId: String): List<Post> = transaction {
        Posts.selectAll().where { Posts.userId eq userId }
            .map { it.toPost() }
    }

    override fun findByCategory(category: String): List<Post> = transaction {
        Posts.selectAll().where { Posts.category eq category }
            .map { it.toPost() }
    }

    override fun addTag(postId: String, tag: String): Boolean = transaction {
        val tagId = PostTags.selectAll().where { PostTags.name eq tag }
            .singleOrNull()?.get(PostTags.id)?.value ?: PostTags.insertAndGetId {
            it[name] = tag
        }.value

        val exists = PostTagRelations.selectAll()
            .where { (PostTagRelations.postId eq postId.toInt()) and (PostTagRelations.tagId eq tagId) }.any()

        if (!exists) {
            PostTagRelations.insert {
                it[PostTagRelations.postId] = postId.toInt()  // 注意转换类型
                it[PostTagRelations.tagId] = tagId
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

        val tagId = PostTags.select(PostTags.id).where { PostTags.name eq tag }
            .singleOrNull()?.get(PostTags.id)?.value

        // if (tagId != null) {
        //     PostTagRelations.deleteWhere {
        //         (PostTagRelations.postId eq postId.toInt()) and (PostTagRelations.tagId eq tagId)
        //     } > 0  // 删除成功则返回 true，否则返回 false
        // } else {
        //     false  // 标签不存在则返回 false
        // }

        if (tagId != null) {
            PostTagRelations.deleteWhere {
                (PostTagRelations.postId eq intPostId) and (PostTagRelations.tagId eq tagId)
            } > 0  // 返回 Boolean 类型，表示是否成功删除
        } else {
            false
        }
    }

    override fun findByTag(tag: String): List<Post> = transaction {
        (PostTags innerJoin com.example.models.databaseTableModels.community.post.mPost.PostTagRelations innerJoin com.example.models.databaseTableModels.community.post.mPost.Posts)
            .selectAll().where { PostTags.name eq tag }
            .map { it.toPost() }
            .distinctBy { it.postId }
    }

    override fun add(item: Post): Post = transaction {
        val postId = Posts.insert {
            it[userId] = item.userId
            it[title] = item.title
            it[createdAt] = item.createdAt
            it[updatedAt] = item.updatedAt
            it[category] = item.category
        } get Posts.id

        findById(postId.value.toString()) ?: throw RuntimeException("未能成功插入推送条目 $postId")
    }

    override fun findById(id: String): Post? = transaction {
        Posts.selectAll().where { Posts.id eq id.toInt() }
            .mapNotNull { it.toPost() }
            .singleOrNull()
    }

    override fun findAll(): List<Post?> = transaction {
        Posts.selectAll()
            .map { it.toPost() }
    }

    override fun update(item: Post): Post = transaction {
        Posts.update({ Posts.id eq item.postId.toInt() }) {
            it[title] = item.title
            it[updatedAt] = LocalDateTime.now()
            it[category] = item.category
        }
        findById(item.postId) ?: throw RuntimeException("Post with ID ${item.postId} not found")
    }

    override fun delete(id: String): Boolean = transaction {
        Posts.deleteWhere { Posts.id eq id.toInt() } > 0
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
        PostTagRelations.join(
            PostTags,
            JoinType.INNER,
            additionalConstraint = { PostTagRelations.tagId eq PostTags.id })
            .selectAll().where { PostTagRelations.postId eq postId.toInt() }
            .map { it[PostTags.name] }
    }

    // 在数据库层面获取推送内容详情
    private fun getPostContents(postId: String): List<Content> = transaction {
        val intPostId = postId.toInt()  // 将 postId 字符串转为整数
        val contents = mutableListOf<Content>() // 在推送内容组成方式上采用可变列表
        val contentEntries = Contents.selectAll().where { Contents.postId eq intPostId }.toList()

        contentEntries.forEach { row ->
            when (row[Contents.type]) {
                ContentType.TextContent -> {
                    val textContent = TextContents.selectAll()
                        .where { TextContents.contentId eq row[Contents.contentId] }
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
                    val imageContent = ImageContents.selectAll()
                        .where { ImageContents.contentId eq row[Contents.contentId] }
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
                    val videoContent = VideoContents.selectAll()
                        .where { VideoContents.contentId eq row[Contents.contentId] }
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
