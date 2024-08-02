package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.sql.Table

object PostCategories : Table("PostCategories") {
    private val categoryId = varchar("categoryId", 50)
    val name = varchar("name", 255).uniqueIndex()

    // 使用最新方式确定主键
    override val primaryKey = PrimaryKey(categoryId, name = "PK_PostCategories_CategoryId")
}