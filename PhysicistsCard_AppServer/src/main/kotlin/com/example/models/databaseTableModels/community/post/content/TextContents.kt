package com.example.models.databaseTableModels.community.post.content

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// TextContents 表，关联到 Contents 表
object TextContents : Table("TextContents") {
    val contentId = reference("contentId", Contents.id, onDelete = ReferenceOption.CASCADE) // 使用 UUID 类型
    val text = text("text")
    override val primaryKey = PrimaryKey(contentId, name = "PK_TextContents_contentId")
}
