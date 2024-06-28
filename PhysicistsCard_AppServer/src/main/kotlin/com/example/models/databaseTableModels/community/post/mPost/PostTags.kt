package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.dao.id.IntIdTable

object PostTags : IntIdTable() {
    val name = varchar("name", 255).uniqueIndex()  // 确保标签名称唯一
}


