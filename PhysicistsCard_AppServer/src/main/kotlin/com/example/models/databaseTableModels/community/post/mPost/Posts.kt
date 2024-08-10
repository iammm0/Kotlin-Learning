package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

// Posts 表使用 UUID 作为主键
object Posts : UUIDTable() {
    val userId = varchar("userid", 50) // 发布者ID
    val title = varchar("title", 255)
    val createdAt = datetime("createdat")
    val updatedAt = datetime("updatedat").nullable()
    val category = reference("category", PostCategories.categoryId).nullable() // 外键引用到 PostCategories 的 categoryId
}