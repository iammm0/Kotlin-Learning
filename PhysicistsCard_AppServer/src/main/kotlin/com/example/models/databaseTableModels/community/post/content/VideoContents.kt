package com.example.models.databaseTableModels.community.post.content

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// VideoContents 表，关联到 Contents 表
object VideoContents : Table("VideoContents") {
    val contentId = reference("contentId", Contents.id, onDelete = ReferenceOption.CASCADE) // 使用 UUID 类型
    val videoUrl = varchar("videoUrl", 255)
    override val primaryKey = PrimaryKey(contentId, name = "PK_VideoContents_contentId")
}