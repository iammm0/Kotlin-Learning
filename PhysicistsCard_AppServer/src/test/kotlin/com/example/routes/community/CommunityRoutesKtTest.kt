package com.example.routes.community

import com.example.module
import io.ktor.client.request.*
import io.ktor.server.testing.*
import kotlin.test.Test

class CommunityRoutesKtTest {

    @Test
    fun testGetCommunityPostsPostidStats() = testApplication {
        application {
            module()
        }
        client.get("/community/posts/{postId}/stats").apply {
            TODO("Please write your test here")
        }
    }
}