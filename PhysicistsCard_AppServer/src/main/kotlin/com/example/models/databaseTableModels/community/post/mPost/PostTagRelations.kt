package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PostTagRelations : Table("PostTagRelations") {
    val postId = reference("postId", Posts.id, onDelete = ReferenceOption.CASCADE)
    val tagId = reference("tagId", PostTags.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(
        postId,
        tagId, name = "PK_PostTagRelations")
}

