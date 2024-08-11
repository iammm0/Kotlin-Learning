package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.sql.Table

// PostCategories 表，主键仍为 categoryId
object PostCategories : Table("postcategories") {
    val categoryId = uuid("categoryid").autoGenerate()
    val name = varchar("name", 255).uniqueIndex()

    override val primaryKey = PrimaryKey(categoryId, name = "PK_PostCategories_CategoryId")
}