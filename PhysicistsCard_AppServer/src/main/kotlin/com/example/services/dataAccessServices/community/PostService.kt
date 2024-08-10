package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.Content
import com.example.models.transmissionModels.community.post.Post
import com.example.repositories.community.IPostRepository
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class PostService(
    private val postRepository: IPostRepository,
    private val contentService: IContentService
) : IPostService {

    override fun createPost(userId: String, title: String, contents: List<Content>, category: String?, tags: List<String>): Post {
        return transaction {
            val post = Post(
                postId = UUID.randomUUID(),
                userId = userId,
                title = title,
                contents = emptyList(),
                createdAt = java.time.LocalDateTime.now(),
                updatedAt = null,
                category = category,
                tags = emptyList()
            )

            // 将帖子存储到数据库
            val savedPost = postRepository.add(post)

            // 存储内容并关联到帖子
            contents.forEach { content ->
                contentService.addContentToPost(savedPost.postId, content)
            }

            // 存储标签并关联到帖子
            tags.forEach { tag ->
                postRepository.addTag(savedPost.postId, tag)
            }

            // 获取更新后的内容列表
            val updatedContents = contentService.getContentsByPostId(savedPost.postId)
            val updatedTags = postRepository.getPostTags(savedPost.postId)

            // 返回包含内容的帖子对象
            savedPost.copy(contents = updatedContents, tags = updatedTags)
        }
    }

    override fun updatePost(postId: UUID, title: String?, contents: List<Content>?, category: String?): Boolean {
        return transaction {
            // 查找现有的帖子
            val post = postRepository.findById(postId) ?: return@transaction false

            // 更新帖子字段
            val updatedPost = post.copy(
                title = title ?: post.title,
                category = category ?: post.category,
                updatedAt = java.time.LocalDateTime.now()
            )

            // 更新帖子信息
            postRepository.update(updatedPost)

            // 如果提供了内容列表，则更新或添加内容
            contents?.forEach { content ->
                if (contentService.getContentsByPostId(content.contentId).isEmpty()) {
                    contentService.addContentToPost(postId, content)
                } else {
                    contentService.updateContent(content)
                }
            }

            true
        }
    }

    override fun getAllPosts(): List<Post> {
        return transaction {
            postRepository.findAll().map { post ->
                post.copy(contents = contentService.getContentsByPostId(post.postId))
            }
        }
    }

    override fun deletePost(postId: UUID): Boolean {
        return transaction {
            // 首先删除与帖子关联的内容
            contentService.getContentsByPostId(postId).forEach { content ->
                contentService.removeContentFromPost(content.contentId)
            }

            // 然后删除帖子本身
            postRepository.delete(postId)
        }
    }

    override fun findPostById(postId: UUID): Post? {
        return transaction {
            postRepository.findById(postId)?.copy(contents = contentService.getContentsByPostId(postId))
        }
    }

    override fun listPostsByUserId(userId: String): List<Post> {
        return transaction {
            postRepository.findByAuthorId(userId).map { post ->
                post.copy(contents = contentService.getContentsByPostId(post.postId))
            }
        }
    }
}
