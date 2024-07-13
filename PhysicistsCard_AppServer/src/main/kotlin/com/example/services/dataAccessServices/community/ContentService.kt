package com.example.services.dataAccessServices.community

import com.example.models.transmissionModels.community.post.Content

class ContentService : IContentService {
    override fun addContentToPost(postId: String, content: Content): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeContentFromPost(postId: String, contentId: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateContent(postId: String, content: Content): Boolean {
        TODO("Not yet implemented")
    }
}