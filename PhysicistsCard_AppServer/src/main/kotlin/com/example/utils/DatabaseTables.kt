package com.example.utils

import com.example.models.databaseTableModels.community.interaction.PostStats
import com.example.models.databaseTableModels.community.interaction.comment.UserComments
import com.example.models.databaseTableModels.community.interaction.favorite.UserFavorites
import com.example.models.databaseTableModels.community.interaction.like.UserLikes
import com.example.models.databaseTableModels.community.post.content.Contents
import com.example.models.databaseTableModels.community.post.content.ImageContents
import com.example.models.databaseTableModels.community.post.content.TextContents
import com.example.models.databaseTableModels.community.post.content.VideoContents
import com.example.models.databaseTableModels.community.post.mPost.PostCategories
import com.example.models.databaseTableModels.community.post.mPost.PostTagRelations
import com.example.models.databaseTableModels.community.post.mPost.PostTags
import com.example.models.databaseTableModels.community.post.mPost.Posts
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.SchemaUtils.createMissingTablesAndColumns
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseTables {
    fun init() {
        transaction {
            SchemaUtils.create(
                UserFavorites,
                UserLikes,
                PostStats,
                PostCategories,
                Posts,
                Contents,
                ImageContents,
                TextContents,
                VideoContents,
                PostTags,
                PostTagRelations,
                UserComments,
            )
        }
    }
}
