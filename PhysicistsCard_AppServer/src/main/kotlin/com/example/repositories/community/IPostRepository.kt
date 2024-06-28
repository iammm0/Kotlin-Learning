package com.example.repositories.community

import com.example.models.transmissionModels.community.Post
import com.example.repositories.Repository

// 推送服务基础操作
interface IPostRepository : Repository<Post,String> {

    fun findByAuthorId(userId: String): List<Post>
    // 根据作者ID查找帖子，用于展示特定用户的所有帖子。

    fun findByCategory(category: String): List<Post>
    // 根据帖子分类查找帖子，用于按类别浏览帖子。

    fun addTag(postId: String, tag: String): Boolean
    // 给帖子添加标签，用于丰富帖子的元数据，提高帖子的可搜索性。

    fun removeTag(postId: String, tag: String): Boolean
    // 移除帖子的某个标签，用于更新帖子信息或纠正标签错误。

    fun findByTag(tag: String): List<Post>
    // 根据标签查找帖子，用于按标签筛选和展示帖子。
}