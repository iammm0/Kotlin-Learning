package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.Content
import com.example.models.transmissionModels.community.post.Post

interface IPostService {
    /**
     * 创建新帖子
     *
     * @param userId 用户ID
     * @param title 帖子标题
     * @param contents 帖子内容列表，可以包含文本、图片、视频的组合
     * @param category 帖子分类（可选）
     * @param tags 帖子标签列表（可选）
     * @return 创建的帖子对象
     */
    fun createPost(userId: String, title: String, contents: List<Content>, category: String?, tags: List<String>): Post

    /**
     * 更新现有帖子
     *
     * @param postId 帖子ID
     * @param title 帖子标题（可选）
     * @param contents 帖子内容列表（可选）
     * @param category 帖子分类（可选）
     * @param tags 帖子标签列表（可选）
     * @return 更新帖子是否成功
     */
    fun updatePost(postId: String, title: String?, contents: List<Content>?, category: String?, tags: List<String>?): Boolean

    /**
     * 获取所有帖子
     *
     * @return 帖子列表
     */
    fun getAllPosts(): List<Post?>

    /**
     * 根据帖子ID删除帖子
     *
     * @param postId 帖子ID
     * @return 删除帖子是否成功
     */
    fun deletePost(postId: String): Boolean

    /**
     * 根据帖子ID查询帖子
     *
     * @param postId 帖子ID
     * @return 帖子对象或null（如果未找到）
     */
    fun findPostById(postId: String): Post?

    /**
     * 根据用户ID列出该用户创建的所有帖子
     *
     * @param userId 用户ID
     * @return 帖子列表
     */
    fun listPostsByUserId(userId: String): List<Post>
}
