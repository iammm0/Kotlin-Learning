package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.Post
import com.example.repositories.community.PostRepository

class PostService(private val postRepository: PostRepository) {
    // 根据作者ID获取帖子
    fun getPostsByAuthorId(authorId: String): List<Post> {
        return postRepository.findByAuthorId(authorId)
    }

    // 根据类别获取帖子
    fun getPostsByCategory(category: String): List<Post> {
        return postRepository.findByCategory(category)
    }

    // 添加标签到帖子
    fun addTagToPost(postId: String, tag: String): Boolean {
        return postRepository.addTag(postId, tag)
    }

    // 从帖子移除标签
    fun removeTagFromPost(postId: String, tag: String): Boolean {
        return postRepository.removeTag(postId, tag)
    }

    // 根据标签获取帖子
    fun getPostsByTag(tag: String): List<Post> {
        return postRepository.findByTag(tag)
    }

    // 添加帖子
    fun addPost(post: Post): Post {
        return postRepository.add(post)
    }

    // 根据ID获取帖子
    fun getPostById(id: String): Post? {
        return postRepository.findById(id)
    }

    // 获取所有帖子
    fun getAllPosts(): List<Post?> {
        return postRepository.findAll()
    }

    // 更新帖子
    fun updatePost(post: Post): Post {
        return postRepository.update(post)
    }

    // 删除帖子
    fun deletePost(id: String): Boolean {
        return postRepository.delete(id)
    }
}
