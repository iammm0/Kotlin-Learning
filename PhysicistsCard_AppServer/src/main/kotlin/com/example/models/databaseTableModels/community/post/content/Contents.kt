package com.example.models.databaseTableModels.community.post.content

import com.example.models.databaseTableModels.community.post.mPost.Posts
import com.example.models.transmissionModels.community.post.ContentType
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// Contents 表使用 UUID 作为主键
object Contents : UUIDTable() {
    val postId = reference("postId", Posts.id, onDelete = ReferenceOption.CASCADE) // 引用 Posts 表的主键
    val type = varchar("type", 50)
    val order = integer("contentOrder")
}

