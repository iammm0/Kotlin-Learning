package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.PostStat
import com.example.repositories.community.IPostStatsRepository
import java.util.UUID

class PostStatService(private val postStatRepository: IPostStatsRepository) : IPostStatService {

    override fun getPostStats(postId: UUID): PostStat? {
        return postStatRepository.findByPostId(postId)
    }

    override fun createOrUpdatePostStats(postStat: PostStat): PostStat {
        return postStatRepository.createOrUpdate(postStat)
    }

    override fun updatePostStats(postStat: PostStat): PostStat {
        return postStatRepository.update(postStat)
    }

    override fun deletePostStats(postId: UUID): Boolean {
        return postStatRepository.delete(postId)
    }

    /**
     * 增加帖子的点赞数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    override fun incrementLikes(postId: UUID): PostStat? {
        val currentStat = getPostStats(postId)
        return if (currentStat != null) {
            val updatedStat = currentStat.copy(likesCount = currentStat.likesCount + 1)
            updatePostStats(updatedStat)
        } else {
            createOrUpdatePostStats(PostStat(postId = postId, likesCount = 1))
        }
    }

    /**
     * 增加帖子的评论数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    override fun incrementComments(postId: UUID): PostStat? {
        val currentStat = getPostStats(postId)
        return if (currentStat != null) {
            val updatedStat = currentStat.copy(commentsCount = currentStat.commentsCount + 1)
            updatePostStats(updatedStat)
        } else {
            createOrUpdatePostStats(PostStat(postId = postId, commentsCount = 1))
        }
    }

    /**
     * 增加帖子的收藏数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    override fun incrementFavorites(postId: UUID): PostStat? {
        val currentStat = getPostStats(postId)
        return if (currentStat != null) {
            val updatedStat = currentStat.copy(favoritesCount = currentStat.favoritesCount + 1)
            updatePostStats(updatedStat)
        } else {
            createOrUpdatePostStats(PostStat(postId = postId, favoritesCount = 1))
        }
    }

    /**
     * 减少帖子的点赞数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    override fun decrementLikes(postId: UUID): PostStat? {
        val currentStat = getPostStats(postId)
        return if (currentStat != null && currentStat.likesCount > 0) {
            val updatedStat = currentStat.copy(likesCount = currentStat.likesCount - 1)
            updatePostStats(updatedStat)
        } else {
            currentStat
        }
    }

    /**
     * 减少帖子的评论数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    override fun decrementComments(postId: UUID): PostStat? {
        val currentStat = getPostStats(postId)
        return if (currentStat != null && currentStat.commentsCount > 0) {
            val updatedStat = currentStat.copy(commentsCount = currentStat.commentsCount - 1)
            updatePostStats(updatedStat)
        } else {
            currentStat
        }
    }

    /**
     * 减少帖子的收藏数
     *
     * @param postId 帖子ID
     * @return 更新后的统计信息对象
     */
    override fun decrementFavorites(postId: UUID): PostStat? {
        val currentStat = getPostStats(postId)
        return if (currentStat != null && currentStat.favoritesCount > 0) {
            val updatedStat = currentStat.copy(favoritesCount = currentStat.favoritesCount - 1)
            updatePostStats(updatedStat)
        } else {
            currentStat
        }
    }
}
