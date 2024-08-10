package com.example.services.dataAccessServices.community

import com.example.models.databaseTableModels.community.post.content.*
import com.example.models.transmissionModels.community.post.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

class ContentService : IContentService {

    override fun addContentToPost(postId: UUID, content: Content): Boolean = transaction {
        val contentId = Contents.insertAndGetId {
            it[Contents.postId] = postId
            it[type] = content.type.name
            it[order] = content.order
        }

        when (content) {
            is TextContent -> {
                TextContents.insert {
                    it[TextContents.contentId] = contentId.value
                    it[text] = content.text
                }
            }
            is ImageContent -> {
                ImageContents.insert {
                    it[ImageContents.contentId] = contentId.value
                    it[imageUrl] = content.imageUrl
                }
            }
            is VideoContent -> {
                VideoContents.insert {
                    it[VideoContents.contentId] = contentId.value
                    it[videoUrl] = content.videoUrl
                }
            }
        }
        true
    }

    override fun removeContentFromPost(contentId: UUID): Boolean = transaction {
        // 删除内容相关联的详细信息
        TextContents.deleteWhere { TextContents.contentId eq contentId }
        ImageContents.deleteWhere { ImageContents.contentId eq contentId }
        VideoContents.deleteWhere { VideoContents.contentId eq contentId }

        // 删除内容
        Contents.deleteWhere { Contents.id eq contentId } > 0
    }

    override fun updateContent(content: Content): Boolean = transaction {
        val updated = Contents.update({ Contents.id eq content.contentId }) {
            it[type] = content.type.name
            it[order] = content.order
        }

        when (content) {
            is TextContent -> {
                TextContents.update({ TextContents.contentId eq content.contentId }) {
                    it[text] = content.text
                }
            }
            is ImageContent -> {
                ImageContents.update({ ImageContents.contentId eq content.contentId }) {
                    it[imageUrl] = content.imageUrl
                }
            }
            is VideoContent -> {
                VideoContents.update({ VideoContents.contentId eq content.contentId }) {
                    it[videoUrl] = content.videoUrl
                }
            }
        }
        updated > 0
    }

    override fun getContentsByPostId(postId: UUID): List<Content> = transaction {
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
}
