package com.example.models.databaseTableModels.community.post.content

import org.jetbrains.exposed.sql.Table

object VideoContents : Table("VideoContents") {
    val contentId = integer("contentId").references(com.example.models.databaseTableModels.community.post.content.Contents.contentId)
    val videoUrl = varchar("videoUrl", 255)
    override val primaryKey = PrimaryKey(contentId, name = "PK_VideoContents_contentId")
}