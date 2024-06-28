package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.CommentTargetType
import com.example.models.transmissionModels.community.UserComment
import com.example.repositories.community.UserCommentRepository


class UserCommentService(private val userCommentRepository: UserCommentRepository) {
    // 根据目标ID和类型获取评论
    fun getCommentsByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment> {
        return userCommentRepository.findByTargetId(targetId, targetType)
    }

    // 获取特定评论的回复
    fun getRepliesForComment(commentId: String): List<UserComment> {
        return userCommentRepository.findReplies(commentId)
    }

    // 添加评论
    fun addComment(comment: UserComment): UserComment {
        return userCommentRepository.add(comment)
    }

    // 根据ID获取评论
    fun getCommentById(id: String): UserComment? {
        return userCommentRepository.findById(id)
    }

    // 获取所有评论
    fun getAllComments(): List<UserComment?> {
        return userCommentRepository.findAll()
    }

    // 更新评论
    fun updateComment(comment: UserComment): UserComment {
        return userCommentRepository.update(comment)
    }

    // 删除评论
    fun deleteComment(id: String): Boolean {
        return userCommentRepository.delete(id)
    }
}
