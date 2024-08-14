package com.example.physicistscard.apiServices


import com.example.physicistscard.transmissionModels.auth.user.User
import com.example.physicistscard.utils.TokenManager
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class FriendshipApiService(
    private val client: HttpClient,
    private val tokenManager: TokenManager
) : IFriendshipApiService {

    override suspend fun sendFriendRequest(receiverId: String): Boolean {
        val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
        val response: HttpResponse = client.post {
            url("${NetworkConfig.BASE_URL}/send-friend-request")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(receiverId)
        }
        return response.status == HttpStatusCode.OK
    }

    override suspend fun acceptFriendRequest(friendId: String): Boolean {
        val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
        val response: HttpResponse = client.post {
            url("${NetworkConfig.BASE_URL}/accept-friend-request")
            contentType(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "Bearer $token")
            setBody(friendId)
        }
        return response.status == HttpStatusCode.OK
    }

    override suspend fun getFriends(): List<User> {
        val token = tokenManager.getToken() ?: throw Exception("Token is missing or invalid")
        return client.get {
            url("${NetworkConfig.BASE_URL}/friends")
            header(HttpHeaders.Authorization, "Bearer $token")
        }.body()
    }
}
