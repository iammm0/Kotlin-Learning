package com.example.models.databaseTableModels.community.post.mPost

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object PostTagRelations : Table("PostTagRelations") {
    val postId = reference("postId", com.example.models.databaseTableModels.community.post.mPost.Posts.id, onDelete = ReferenceOption.CASCADE)
    val tagId = reference("tagId", com.example.models.databaseTableModels.community.post.mPost.PostTags.id, onDelete = ReferenceOption.CASCADE)

    override val primaryKey = PrimaryKey(
        com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.postId,
        com.example.models.databaseTableModels.community.post.mPost.PostTagRelations.tagId, name = "PK_PostTagRelations")
}

