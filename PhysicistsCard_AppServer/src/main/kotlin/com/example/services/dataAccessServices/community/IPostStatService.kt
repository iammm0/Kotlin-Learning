package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.PostStat
import java.util.UUID

// 定义 PostStat 服务层的接口
interface IPostStatService {

    /**
     * 创建或更新帖子的统计信息
     *
     * @param postStat 统计信息对象
     * @return 创建或更新后的统计信息对象
     */
    fun createOrUpdatePostStats(postStat: PostStat): PostStat

    /**
     * 更新帖子的统计信息
     *
     * @param postStat 统计信息对象
     * @return 更新后的统计信息对象
     */
    fun updatePostStats(postStat: PostStat): PostStat

    /**
     * 删除帖子的统计信息
     *
     * @param postId 帖子ID
     * @return 是否成功删除
     */
    fun deletePostStats(postId: UUID): Boolean

    /**
     * 增加帖子的点赞数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    fun incrementLikes(postId: UUID): PostStat?

    /**
     * 减少帖子的点赞数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    fun decrementLikes(postId: UUID): PostStat?

    /**
     * 增加帖子的评论数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    fun incrementComments(postId: UUID): PostStat?

    /**
     * 减少帖子的评论数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    fun decrementComments(postId: UUID): PostStat?

    /**
     * 增加帖子的收藏数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    fun incrementFavorites(postId: UUID): PostStat?

    /**
     * 减少帖子的收藏数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    fun decrementFavorites(postId: UUID): PostStat?

    /**
     * 获取帖子的统计信息
     *
     * @param postId 帖子ID
     * @return 帖子的统计信息对象
     */
    fun getPostStats(postId: UUID): PostStat?
}
