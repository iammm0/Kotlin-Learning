package com.example.services.dataAccessServices.community

import IUserCommentRepository
import com.example.models.transmissionModels.community.interaction.CommentTargetType
import com.example.models.transmissionModels.community.interaction.UserComment

class UserCommentService(private val userCommentRepository: IUserCommentRepository) : IUserCommentService {
    override fun addComment(postId: String, comment: UserComment): Boolean {
        return userCommentRepository.add(comment) != null
    }

    override fun getCommentsByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment> {
        return userCommentRepository.findByTargetId(targetId, targetType)
    }

    override fun getRepliesForComment(commentId: String): List<UserComment> {
        return userCommentRepository.findReplies(commentId)
    }

    override fun deleteComment(commentId: String): Boolean {
        return userCommentRepository.delete(commentId)
    }

    override fun countCommentsByTargetId(targetId: String, targetType: CommentTargetType): Int {
        return userCommentRepository.findByTargetId(targetId, targetType).size
    }

    override fun findCommentById(commentId: String): UserComment? {
        return userCommentRepository.findById(commentId)
    }

    override fun add(item: UserComment): UserComment {
        return userCommentRepository.add(item)
    }

    override fun findById(id: String): UserComment? {
        return userCommentRepository.findById(id)
    }

    override fun findAll(): List<UserComment?> {
        return userCommentRepository.findAll()
    }

    override fun update(item: UserComment): UserComment {
        return userCommentRepository.update(item)
    }

    override fun delete(id: String): Boolean {
        return userCommentRepository.delete(id)
    }
}
