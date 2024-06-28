package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.CommentTargetType
import com.example.models.transmissionModels.community.UserComment
import com.example.repositories.Repository

interface IUserCommentService : Repository<UserComment, String> {
    fun getCommentsByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment>
    fun getRepliesForComment(commentId: String): List<UserComment>
}
