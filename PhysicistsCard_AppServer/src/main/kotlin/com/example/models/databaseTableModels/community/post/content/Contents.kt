package com.example.models.databaseTableModels.community.post.content

import com.example.models.transmissionModels.community.post.ContentType
import org.jetbrains.exposed.sql.Table

object Contents : Table("Contents") {
    val contentId = integer("contentId").autoIncrement()
    val postId = integer("postId").references(com.example.models.databaseTableModels.community.post.mPost.Posts.id)
    val type = enumerationByName("type", 10, ContentType::class)
    val order = integer("order") // 内容在帖子中的顺序
    override val primaryKey = PrimaryKey(com.example.models.databaseTableModels.community.post.content.Contents.contentId, name = "PK_Contents_contentId")
}

