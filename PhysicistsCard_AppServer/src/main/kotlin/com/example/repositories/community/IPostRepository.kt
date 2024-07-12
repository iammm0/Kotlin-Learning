package com.example.repositories.community

import com.example.models.transmissionModels.community.post.Post
import com.example.repositories.Repository

// 推送服务基础操作
interface IPostRepository : Repository<Post, String> {
    /**
     * 根据作者ID查找帖子
     *
     * @param userId 作者ID
     * @return 帖子列表
     */
    fun findByAuthorId(userId: String): List<Post>

    /**
     * 根据帖子分类查找帖子
     *
     * @param category 帖子分类
     * @return 帖子列表
     */
    fun findByCategory(category: String): List<Post>

    /**
     * 给帖子添加标签
     *
     * @param postId 帖子ID
     * @param tag 标签
     * @return 添加标签是否成功
     */
    fun addTag(postId: String, tag: String): Boolean

    /**
     * 移除帖子的某个标签
     *
     * @param postId 帖子ID
     * @param tag 标签
     * @return 移除标签是否成功
     */
    fun removeTag(postId: String, tag: String): Boolean

    /**
     * 根据标签查找帖子
     *
     * @param tag 标签
     * @return 帖子列表
     */
    fun findByTag(tag: String): List<Post>
}
