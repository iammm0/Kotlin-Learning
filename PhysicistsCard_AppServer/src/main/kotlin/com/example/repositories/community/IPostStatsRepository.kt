package com.example.repositories.community

import com.example.models.transmissionModels.community.post.PostStat
import java.util.UUID

// 定义 PostStat 数据访问层的接口
interface IPostStatsRepository {

    /**
     * 根据帖子ID获取统计信息
     *
     * @param postId 帖子ID
     * @return 帖子的统计信息
     */
    fun findByPostId(postId: UUID): PostStat?

    /**
     * 创建或初始化帖子的统计信息
     *
     * @param postStat 统计信息对象
     * @return 创建后的统计信息对象
     */
    fun createOrUpdate(postStat: PostStat): PostStat

    /**
     * 更新帖子的统计信息
     *
     * @param postStat 统计信息对象
     * @return 更新后的统计信息对象
     */
    fun update(postStat: PostStat): PostStat

    /**
     * 删除帖子的统计信息
     *
     * @param postId 帖子ID
     * @return 是否成功删除
     */
    fun delete(postId: UUID): Boolean
}
