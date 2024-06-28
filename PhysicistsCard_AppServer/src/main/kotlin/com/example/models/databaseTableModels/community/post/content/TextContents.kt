package com.example.models.databaseTableModels.community.post.content

import org.jetbrains.exposed.sql.Table

object TextContents : Table("TextContents") {
    val contentId = integer("contentId").references(com.example.models.databaseTableModels.community.post.content.Contents.contentId)
    val text = text("text")
    override val primaryKey = PrimaryKey(com.example.models.databaseTableModels.community.post.content.TextContents.contentId, name = "PK_TextContents_contentId")
}