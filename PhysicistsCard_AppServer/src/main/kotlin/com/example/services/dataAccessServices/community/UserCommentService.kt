package com.example.services.dataAccessServices.community

import IUserCommentRepository
import com.example.models.transmissionModels.community.interaction.CommentTargetType
import com.example.models.transmissionModels.community.interaction.UserComment

class UserCommentService(private val userCommentRepository: IUserCommentRepository) : IUserCommentService {
    override fun addComment(postId: String, comment: UserComment): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCommentsByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment> {
        TODO("Not yet implemented")
    }

    override fun getRepliesForComment(commentId: String): List<UserComment> {
        TODO("Not yet implemented")
    }

    override fun deleteComment(commentId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun countCommentsByTargetId(targetId: String, targetType: CommentTargetType): Int {
        TODO("Not yet implemented")
    }

    override fun findCommentById(commentId: String): UserComment? {
        TODO("Not yet implemented")
    }

    override fun add(item: UserComment): UserComment {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): UserComment? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<UserComment?> {
        TODO("Not yet implemented")
    }

    override fun update(item: UserComment): UserComment {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Boolean {
        TODO("Not yet implemented")
    }

}
