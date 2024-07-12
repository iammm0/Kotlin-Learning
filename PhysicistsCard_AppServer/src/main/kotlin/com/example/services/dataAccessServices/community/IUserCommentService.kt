package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.interaction.CommentTargetType
import com.example.models.transmissionModels.community.interaction.UserComment
import com.example.repositories.Repository

interface IUserCommentService : Repository<UserComment, String> {
    /**
     * 在特定推送下添加评论
     *
     * @param postId 推送ID
     * @param comment 评论内容
     * @return 添加评论是否成功
     */
    fun addComment(postId: String, comment: UserComment): Boolean

    /**
     * 获取目标对象的所有评论
     *
     * @param targetId 目标对象ID
     * @param targetType 目标对象类型（推送或评论）
     * @return 评论列表
     */
    fun getCommentsByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment>

    /**
     * 获取特定评论的所有回复
     *
     * @param commentId 评论ID
     * @return 回复列表
     */
    fun getRepliesForComment(commentId: String): List<UserComment>

    /**
     * 删除特定评论
     *
     * @param commentId 评论ID
     * @return 删除评论是否成功
     */
    fun deleteComment(commentId: String): Boolean
}

