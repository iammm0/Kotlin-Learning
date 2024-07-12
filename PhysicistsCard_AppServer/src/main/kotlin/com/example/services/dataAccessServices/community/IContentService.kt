package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.Content

interface IContentService {
    /**
     * 向指定帖子添加内容项
     *
     * @param postId 帖子ID
     * @param content 内容对象
     * @return 添加内容是否成功
     */
    fun addContentToPost(postId: String, content: Content): Boolean

    /**
     * 从帖子中移除指定的内容项
     *
     * @param postId 帖子ID
     * @param contentId 内容项ID
     * @return 移除内容是否成功
     */
    fun removeContentFromPost(postId: String, contentId: Int): Boolean

    /**
     * 更新帖子中的内容项
     *
     * @param postId 帖子ID
     * @param content 内容对象
     * @return 更新内容是否成功
     */
    fun updateContent(postId: String, content: Content): Boolean
}
