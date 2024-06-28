package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.Content
import com.example.models.transmissionModels.community.Post

// 处理帖子内容的操作，如添加、更新、删除特定类型的内容
interface IPostService {
    fun createPost(userId: String, title: String, contents: List<Content>, category: String?, tags: List<String>): Post
    // 创建新帖子。内容可以是文本、图片、视频的组合。

    fun updatePost(postId: String, title: String?, contents: List<Content>?, category: String?, tags: List<String>?): Boolean
    // 更新现有帖子。所有参数均为可选，只更新提供的字段。

    fun deletePost(postId: String): Boolean
    // 根据postId删除帖子。

    fun findPostById(postId: String): Post?
    // 根据postId查询帖子。

    fun listPostsByUserId(userId: String): List<Post>
    // 根据用户ID列出该用户创建的所有帖子。
}