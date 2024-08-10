package com.example.repositories.community

import com.example.models.transmissionModels.community.post.Content
import com.example.models.transmissionModels.community.post.Post
import java.util.*

interface IPostRepository {

    /**
     * 根据帖子ID查找帖子
     *
     * @param id 帖子ID
     * @return 帖子对象或null
     */
    fun findById(id: UUID): Post?

    /**
     * 根据作者ID查找帖子
     *
     * @param userId 作者ID
     * @return 帖子列表
     */
    fun findByAuthorId(userId: String): List<Post>

    /**
     * 查找所有帖子
     *
     * @return 帖子列表
     */
    fun findAll(): List<Post>

    /**
     * 根据帖子分类查找帖子
     *
     * @param category 帖子分类ID
     * @return 帖子列表
     */
    fun findByCategory(category: UUID): List<Post>

    /**
     * 给帖子添加标签
     *
     * @param postId 帖子ID
     * @param tag 标签
     * @return 添加标签是否成功
     */
    fun addTag(postId: UUID, tag: String): Boolean

    /**
     * 移除帖子的某个标签
     *
     * @param postId 帖子ID
     * @param tag 标签
     * @return 移除标签是否成功
     */
    fun removeTag(postId: UUID, tag: String): Boolean

    /**
     * 根据标签查找帖子
     *
     * @param tag 标签
     * @return 帖子列表
     */
    fun findByTag(tag: String): List<Post>

    /**
     * 添加帖子
     *
     * @param item 帖子对象
     * @return 添加后的帖子对象
     */
    fun add(item: Post): Post

    /**
     * 更新帖子
     *
     * @param item 帖子对象
     * @return 更新后的帖子对象
     */
    fun update(item: Post): Post

    /**
     * 删除帖子
     *
     * @param id 帖子ID
     * @return 是否成功删除
     */
    fun delete(id: UUID): Boolean

    // 私有方法 (可选)
    /**
     * 获取帖子的标签
     *
     * @param postId 帖子ID
     * @return 标签列表
     */
    fun getPostTags(postId: UUID): List<String>

    /**
     * 获取帖子的内容
     *
     * @param postId 帖子ID
     * @return 内容列表
     */
    fun getPostContents(postId: UUID): List<Content>
}
