package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

// PostTagRelations 表，管理 Posts 与 Tags 的多对多关系
object PostTagRelations : Table("postTagrelations") {
    val postId = reference("postId", Posts.id, onDelete = ReferenceOption.CASCADE) // 引用 Posts 表的主键
    val tagId = reference("tagId", PostTags.id, onDelete = ReferenceOption.CASCADE) // 引用 PostTags 表的主键

    override val primaryKey = PrimaryKey(postId, tagId, name = "PK_PostTagRelations")
}
