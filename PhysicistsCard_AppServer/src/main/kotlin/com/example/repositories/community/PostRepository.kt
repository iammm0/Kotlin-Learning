package com.example.repositories.community

import com.example.models.databaseTableModels.community.post.content.*
import com.example.models.databaseTableModels.community.post.mPost.*
import com.example.models.transmissionModels.community.post.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class PostRepository : IPostRepository {
    override fun findByAuthorId(userId: String): List<Post> = transaction {
        Posts.selectAll().where { Posts.userId eq userId }
            .map { it.toPost() }
    }

    override fun findByCategory(categoryId: UUID): List<Post> = transaction {
        Posts.selectAll().where { Posts.category eq categoryId }
            .map { it.toPost() }
    }

    override fun addTag(postId: UUID, tag: String): Boolean = transaction {
        val tagId = PostTags.selectAll().where { PostTags.name eq tag }
            .map { it[PostTags.id] }
            .singleOrNull() ?: PostTags.insertAndGetId { it[name] = tag }

        val exists = PostTagRelations.selectAll()
            .where { (PostTagRelations.postId eq postId) and (PostTagRelations.tagId eq tagId.value) }.count() > 0

        if (!exists) {
            PostTagRelations.insert {
                it[PostTagRelations.postId] = postId
                it[PostTagRelations.tagId] = tagId.value
            }
            true
        } else {
            false
        }
    }


    override fun removeTag(postId: UUID, tag: String): Boolean = transaction {
        val tagId = PostTags.selectAll().where { PostTags.name eq tag }
            .map { it[PostTags.id] }
            .singleOrNull() ?: return@transaction false

        PostTagRelations.deleteWhere {
            (PostTagRelations.postId eq postId) and (PostTagRelations.tagId eq tagId)
        } > 0
    }

    override fun findByTag(tag: String): List<Post> = transaction {
        (PostTags innerJoin PostTagRelations innerJoin Posts)
            .selectAll().where { PostTags.name eq tag }
            .map { it.toPost() }
    }

    override fun add(item: Post): Post = transaction {
        val postId = Posts.insertAndGetId {
            it[id] = UUID.randomUUID()
            it[userId] = item.userId
            it[title] = item.title
            it[createdAt] = item.createdAt
            it[updatedAt] = item.updatedAt
            it[category] = item.category?.let { cat -> UUID.fromString(cat) }
        }

        item.tags.forEach { addTag(postId.value, it) }
        findById(postId.value) ?: throw RuntimeException("Failed to add post")
    }

    override fun update(item: Post): Post = transaction {
        Posts.update({ Posts.id eq item.postId }) {
            it[title] = item.title
            it[updatedAt] = item.updatedAt
            it[category] = item.category?.let { cat -> UUID.fromString(cat) }
        }
        item.tags.forEach { addTag(item.postId, it) }
        findById(item.postId) ?: throw RuntimeException("Post with ID ${item.postId} not found")
    }

    override fun findById(id: UUID): Post? = transaction {
        Posts.selectAll().where { Posts.id eq id }
            .mapNotNull { it.toPost() }
            .singleOrNull()
    }

    override fun findAll(): List<Post> = transaction {
        Posts.selectAll()
            .map { it.toPost() }
    }

    override fun delete(id: UUID): Boolean = transaction {
        Posts.deleteWhere { Posts.id eq id } > 0
    }

    private fun ResultRow.toPost(): Post {
        val categoryId = this[Posts.category]
        val categoryName = categoryId?.let { id ->
            PostCategories.selectAll().where { PostCategories.categoryId eq id }
                .singleOrNull()?.get(PostCategories.name)
        }

        return Post(
            postId = this[Posts.id].value,
            userId = this[Posts.userId],
            title = this[Posts.title],
            createdAt = this[Posts.createdAt],
            updatedAt = this[Posts.updatedAt],
            category = categoryName,
            tags = getPostTags(this[Posts.id].value),
            contents = getPostContents(this[Posts.id].value)
        )
    }

    override fun getPostContents(postId: UUID): List<Content> = transaction {
        val contents = mutableListOf<Content>()
        val contentEntries = Contents.selectAll().where { Contents.postId eq postId }.toList()

        contentEntries.forEach { row ->
            when (row[Contents.type]) {
                ContentType.TextContent.name -> {
                    val textContent = TextContents.selectAll().where { TextContents.contentId eq row[Contents.id] }
                        .firstOrNull()?.let {
                            TextContent(
                                contentId = row[Contents.id].value,
                                order = row[Contents.order],
                                text = it[TextContents.text]
                            )
                        }
                    if (textContent != null) contents.add(textContent)
                }

                ContentType.ImageContent.name -> {
                    val imageContent = ImageContents.selectAll().where { ImageContents.contentId eq row[Contents.id] }
                        .firstOrNull()?.let {
                            ImageContent(
                                contentId = row[Contents.id].value,
                                order = row[Contents.order],
                                imageUrl = it[ImageContents.imageUrl]
                            )
                        }
                    if (imageContent != null) contents.add(imageContent)
                }

                ContentType.VideoContent.name -> {
                    val videoContent = VideoContents.selectAll().where { VideoContents.contentId eq row[Contents.id] }
                        .firstOrNull()?.let {
                            VideoContent(
                                contentId = row[Contents.id].value,
                                order = row[Contents.order],
                                videoUrl = it[VideoContents.videoUrl]
                            )
                        }
                    if (videoContent != null) contents.add(videoContent)
                }
            }
        }
        contents.sortedBy { it.order }
    }

    override fun getPostTags(postId: UUID): List<String> = transaction {
        PostTagRelations.join(
            PostTags,
            JoinType.INNER,
            additionalConstraint = { PostTagRelations.tagId eq PostTags.id })
            .selectAll().where { PostTagRelations.postId eq postId }
            .map { it[PostTags.name] }
    }
}
