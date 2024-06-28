package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object Posts : IntIdTable() {
    val userId = varchar("userId", 50) // 发布者ID
    val title = varchar("title", 255)
    val createdAt = datetime("createdAt")
    val updatedAt = datetime("updatedAt").nullable()
    val category = varchar("category", 50).nullable() // 分类
}