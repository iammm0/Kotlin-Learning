package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.Content

// 处理帖子的基本操作
interface IContentService {
    fun addContentToPost(postId: String, content: Content): Boolean
    // 向指定帖子添加内容项。

    fun removeContentFromPost(postId: String, contentId: Int): Boolean
    // 从帖子中移除指定的内容项。

    fun updateContent(postId: String, content: Content): Boolean
    // 更新帖子中的内容项。
}