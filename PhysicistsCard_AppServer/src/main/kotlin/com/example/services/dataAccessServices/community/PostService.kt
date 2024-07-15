package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.Content
import com.example.models.transmissionModels.community.post.Post
import com.example.repositories.community.IPostRepository
import java.time.LocalDateTime
import java.util.*

class PostService(private val postRepository: IPostRepository) : IPostService {

    override fun createPost(
        userId: String,
        title: String,
        contents: List<Content>,
        category: String?,
        tags: List<String>
    ): Post {
        // 实现创建帖子的方法
        val post = Post(
            postId = UUID.randomUUID().toString(),
            userId = userId,
            title = title,
            contents = contents,
            createdAt = LocalDateTime.now(),
            updatedAt = null,
            category = category,
            tags = tags
        )
        return postRepository.add(post)
    }

    override fun updatePost(
        postId: String,
        title: String?,
        contents: List<Content>?,
        category: String?,
        tags: List<String>?
    ): Boolean {
        // 实现更新帖子的逻辑
        val post = postRepository.findById(postId) ?: return false
        post.copy(
            title = title ?: post.title,
            contents = contents ?: post.contents,
            category = category ?: post.category,
            tags = tags ?: post.tags,
            updatedAt = LocalDateTime.now()
        )
        return true
    }

    override fun getAllPosts(): List<Post?> {
        return postRepository.findAll()
    }

    override fun deletePost(postId: String): Boolean {
        return postRepository.delete(postId)
    }

    override fun findPostById(postId: String): Post? {
        return postRepository.findById(postId)
    }

    override fun listPostsByUserId(userId: String): List<Post> {
        return postRepository.findByAuthorId(userId)
    }
}
