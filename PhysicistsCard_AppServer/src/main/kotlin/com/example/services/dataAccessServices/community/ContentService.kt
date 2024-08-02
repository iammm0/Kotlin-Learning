package com.example.services.dataAccessServices.community

import com.example.models.databaseTableModels.community.post.content.Contents
import com.example.models.databaseTableModels.community.post.content.ImageContents
import com.example.models.databaseTableModels.community.post.content.TextContents
import com.example.models.databaseTableModels.community.post.content.VideoContents
import com.example.models.transmissionModels.community.post.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ContentService : IContentService {

    override fun addContentToPost(
        postId: String,
        content: Content,
        imageContent: ImageContent,
        textContent: TextContent,
        videoContent: VideoContent
    ): Boolean {
        return try {
            transaction {
                val contentId = Contents.insert {
                    it[Contents.postId] = postId.toInt()
                    it[type] = content.type
                    it[order] = content.order
                } get Contents.contentId


                if (contentId != null) {
                    when (content.type) {
                        ContentType.ImageContent -> {
                            ImageContents.insert {
                                it[ImageContents.contentId] = contentId
                                it[imageUrl] = imageContent.imageUrl
                            }
                        }
                        ContentType.TextContent -> {
                            TextContents.insert {
                                it[TextContents.contentId] = contentId
                                it[text] = textContent.text
                            }
                        }
                        ContentType.VideoContent -> {
                            VideoContents.insert {
                                it[VideoContents.contentId] = contentId
                                it[videoUrl] = videoContent.videoUrl
                            }
                        }
                    }
                } else {
                    throw Exception("Failed to retrieve content ID")
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun removeContentFromPost(postId: String, contentId: Int): Boolean {
        return try {
            transaction {
                val content = Contents.selectAll()
                    .where { Contents.contentId eq contentId and (Contents.postId eq postId.toInt()) }
                    .firstOrNull() ?: return@transaction false

                when (content[Contents.type]) {
                    ContentType.ImageContent -> ImageContents.deleteWhere { ImageContents.contentId eq contentId }
                    ContentType.TextContent -> TextContents.deleteWhere { TextContents.contentId eq contentId }
                    ContentType.VideoContent -> VideoContents.deleteWhere { VideoContents.contentId eq contentId }
                }

                Contents.deleteWhere { Contents.contentId eq contentId }
            }
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun updateContent(postId: String, content: Content): Boolean {
        return try {
            transaction {
                val contentToUpdate = Contents.selectAll()
                    .where { Contents.contentId eq content.contentId and (Contents.postId eq postId.toInt()) }
                    .firstOrNull() ?: return@transaction false

                when (content.type) {
                    ContentType.ImageContent -> ImageContents.update({ ImageContents.contentId eq content.contentId }) {
                        it[imageUrl] = imageUrl
                    }
                    ContentType.TextContent -> TextContents.update({ TextContents.contentId eq content.contentId }) {
                        it[text] = text
                    }
                    ContentType.VideoContent -> VideoContents.update({ VideoContents.contentId eq content.contentId }) {
                        it[videoUrl] = videoUrl
                    }
                }

                Contents.update({ Contents.contentId eq content.contentId }) {
                    it[type] = content.type
                    it[order] = content.order
                }
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
