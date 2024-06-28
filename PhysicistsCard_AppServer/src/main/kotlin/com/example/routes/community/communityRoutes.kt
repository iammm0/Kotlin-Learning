package com.example.routes.community

import com.example.models.transmissionModels.community.*
import com.example.services.dataAccessServices.community.PostService
import com.example.services.dataAccessServices.community.UserCommentService
import com.example.services.dataAccessServices.community.UserFavoriteService
import com.example.services.dataAccessServices.community.UserLikeService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.communityRoutes(
    postService: PostService,
    commentService: UserCommentService,
    likeService: UserLikeService,
    favoriteService: UserFavoriteService
) {
    routing {

        route("/posts") {
            get("/") {
                try {
                    call.respond(HttpStatusCode.OK, postService.getAllPosts())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取所有推送时发生错误: ${e.localizedMessage}")
                }
            }

            get("/{postId}") {
                val postId = call.parameters["postId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val post = postService.getPostById(postId)
                    if (post != null) {
                        call.respond(HttpStatusCode.OK, post)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到推送")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取推送时发生错误: ${e.localizedMessage}")
                }
            }

            post("/") {
                try {
                    val requestBody = call.receiveText()
                    println("Received request body: $requestBody")
                    val post = Json.decodeFromString<Post>(requestBody)
                    val newPost = postService.addPost(post)
                    call.respond(HttpStatusCode.Created, newPost)
                } catch (e: Exception) {
                    println("Error deserializing Post: ${e.message}")
                    call.respond(HttpStatusCode.BadRequest, "数据格式无效: ${e.localizedMessage}")
                }
            }

            put("/{postId}") {
                val postId = call.parameters["postId"] ?: return@put call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val requestBody = call.receiveText()
                    println("Received request body: $requestBody")
                    val updatePost = Json.decodeFromString<Post>(requestBody)
                    val updated = postService.updatePost(updatePost.copy(postId = postId))
                    call.respond(HttpStatusCode.OK, updated)
                } catch (e: Exception) {
                    println("Error deserializing Post: ${e.message}")
                    call.respond(HttpStatusCode.BadRequest, "数据格式无效: ${e.localizedMessage}")
                }
            }

            delete("/{postId}") {
                val postId = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    if (postService.deletePost(postId)) {
                        call.respond(HttpStatusCode.OK, "推送已删除")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到推送")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "删除推送时发生错误: ${e.localizedMessage}")
                }
            }
        }

        route("/comments/{postId}") {
            post("/") {
                val postId = call.parameters["postId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val requestBody = call.receiveText()
                    println("Received request body: $requestBody")
                    val comment = Json.decodeFromString<UserComment>(requestBody)
                    val newComment = commentService.addComment(comment.copy(targetId = postId))
                    call.respond(HttpStatusCode.Created, newComment)
                } catch (e: Exception) {
                    println("Error deserializing UserComment: ${e.message}")
                    call.respond(HttpStatusCode.InternalServerError, "发表评论时发生错误: ${e.localizedMessage}")
                }
            }

            get("/") {
                val postId = call.parameters["postId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val comments = commentService.getCommentsByTargetId(postId, CommentTargetType.POST)
                    call.respond(HttpStatusCode.OK, comments)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取评论时发生错误: ${e.localizedMessage}")
                }
            }

            delete("/{commentId}") {
                val commentId = call.parameters["commentId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的评论ID")
                try {
                    if (commentService.deleteComment(commentId)) {
                        call.respond(HttpStatusCode.OK, "评论已删除")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到评论")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "删除评论时发生错误: ${e.localizedMessage}")
                }
            }
        }

        route("/posts/{postId}") {
            get("/") {
                val postId = call.parameters["postId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val post = postService.getPostById(postId)
                    if (post != null) {
                        call.respond(HttpStatusCode.OK, post)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到推送")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取推送详情时发生错误: ${e.localizedMessage}")
                }
            }

            post("/like") {
                val postId = call.parameters["postId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val userId = call.receive<String>()
                    val userLike = likeService.addLike(userId, postId, LikeTargetType.POST)
                    call.respond(HttpStatusCode.Created, userLike)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "点赞时发生错误: ${e.localizedMessage}")
                }
            }

            delete("/like") {
                val postId = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val userId = call.receive<String>()
                    if (likeService.removeLike(userId, postId, LikeTargetType.POST)) {
                        call.respond(HttpStatusCode.OK, "点赞已取消")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到点赞记录")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "取消点赞时发生错误: ${e.localizedMessage}")
                }
            }

            post("/favorite") {
                val postId = call.parameters["postId"] ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val userId = call.receive<String>()
                    val userFavorite = favoriteService.addFavorite(userId, postId, FavoriteTargetType.POST)
                    call.respond(HttpStatusCode.Created, userFavorite)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "收藏时发生错误: ${e.localizedMessage}")
                }
            }

            delete("/favorite") {
                val postId = call.parameters["postId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val userId = call.receive<String>()
                    if (favoriteService.removeFavorite(userId, postId, FavoriteTargetType.POST)) {
                        call.respond(HttpStatusCode.OK, "收藏已取消")
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到收藏记录")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "取消收藏时发生错误: ${e.localizedMessage}")
                }
            }

            get("/stats") {
                val postId = call.parameters["postId"] ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val stats = mapOf(
                        "likes" to likeService.countLikes(postId, LikeTargetType.POST),
                        "comments" to commentService.getCommentsByTargetId(postId, CommentTargetType.POST).size
                    )
                    call.respond(HttpStatusCode.OK, stats)
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取推送统计信息时发生错误: ${e.localizedMessage}")
                }
            }
        }
    }
}