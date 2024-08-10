package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.Content
import java.util.UUID

interface IContentService {
    /**
     * 向指定帖子添加内容项
     *
     * @param postId 帖子ID
     * @param content 内容对象
     * @return 添加内容是否成功
     */
    fun addContentToPost(postId: UUID, content: Content): Boolean

    /**
     * 从帖子中移除指定的内容项
     *
     * @param contentId 内容项ID
     * @return 移除内容是否成功
     */
    fun removeContentFromPost(contentId: UUID): Boolean

    /**
     * 更新帖子中的内容项
     *
     * @param content 内容对象
     * @return 更新内容是否成功
     */
    fun updateContent(content: Content): Boolean

    /**
     * 获取帖子的所有内容
     *
     * @param postId 帖子ID
     * @return 内容列表
     */
    fun getContentsByPostId(postId: UUID): List<Content>
}
