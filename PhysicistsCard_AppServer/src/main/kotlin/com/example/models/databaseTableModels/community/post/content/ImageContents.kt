package com.example.models.databaseTableModels.community.post.content

import org.jetbrains.exposed.sql.Table

object ImageContents : Table("ImageContents") {
    val contentId = integer("contentId").references(com.example.models.databaseTableModels.community.post.content.Contents.contentId)
    val imageUrl = varchar("imageUrl", 255)
    override val primaryKey = PrimaryKey(com.example.models.databaseTableModels.community.post.content.ImageContents.contentId, name = "PK_ImageContents_contentId")
}