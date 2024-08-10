package com.example.routes.community

import com.example.models.transmissionModels.auth.user.Role
import com.example.models.transmissionModels.community.interaction.CommentTargetType
import com.example.models.transmissionModels.community.interaction.FavoriteTargetType
import com.example.models.transmissionModels.community.interaction.LikeTargetType
import com.example.models.transmissionModels.community.interaction.UserComment
import com.example.models.transmissionModels.community.post.Post
import com.example.services.dataAccessServices.community.*
import com.example.utils.hasAnyRole
import com.example.utils.hasRole
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*


fun Application.communityRoutes(
    postService: IPostService,
    commentService: IUserCommentService,
    likeService: IUserLikeService,
    favoriteService: IUserFavoriteService
) {
    routing {
        // 推送相关
        route("/community/posts") {
            // 获取社区中所有推送内容
            get("/") {
                try {
                    call.respond(HttpStatusCode.OK, postService.getAllPosts())
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取所有推送时发生错误: ${e.localizedMessage}")
                }
            }
            // 获取社区中特定推送的详情
            get("/{postId}") {
                val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                    ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                try {
                    val post = postService.findPostById(postId)
                    if (post != null) {
                        call.respond(HttpStatusCode.OK, post)
                    } else {
                        call.respond(HttpStatusCode.NotFound, "未找到推送")
                    }
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.InternalServerError, "获取推送详情时发生错误: ${e.localizedMessage}")
                }
            }
            authenticate {
                // 在社区中发表推送
                post("/") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasAnyRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }
                    try {
                        val post = call.receive<Post>()
                        val createdPost = postService.createPost(post.userId, post.title, post.contents, post.category, post.tags)
                        call.respond(HttpStatusCode.Created, createdPost)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "创建推送时发生错误: ${e.localizedMessage}")
                    }
                }
                // 更新特定推送的内容
                put("/{postId}") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.MERCHANT)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@put
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@put call.respond(HttpStatusCode.BadRequest, "无效的推送ID")
                    try {
                        val post = call.receive<Post>()
                        val updated = postService.updatePost(postId, post.title, post.contents, post.category)
                        if (updated) {
                            call.respond(HttpStatusCode.OK, "推送已更新")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "未找到推送")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "更新推送时发生错误: ${e.localizedMessage}")
                    }
                }

                // 删除特定推送
                delete("/{postId}") {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.getClaim("userId")?.asString()
                    val role = principal?.payload?.getClaim("role")?.asString()
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val post = postService.findPostById(postId)
                        if (post != null && (post.userId == userId || role == Role.COMMUNITY_ADMIN.name || role == Role.SUPER_ADMIN.name)) {
                            if (postService.deletePost(postId)) {
                                call.respond(HttpStatusCode.OK, "推送已删除")
                            } else {
                                call.respond(HttpStatusCode.InternalServerError, "删除推送时发生错误")
                            }
                        } else {
                            call.respond(HttpStatusCode.Forbidden, "你没有权限删除此推送")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "删除推送时发生错误: ${e.localizedMessage}")
                    }
                }
            }
        }

        authenticate {
            route("/community/comments/{postId}") {
                // 在特定推送下发表评论
                post("/") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val comment = call.receive<UserComment>()
                        val added = commentService.addComment(postId.toString(), comment)
                        if (added) {
                            call.respond(HttpStatusCode.Created, "评论已添加")
                        } else {
                            call.respond(HttpStatusCode.InternalServerError, "评论添加失败")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "发表评论时发生错误: ${e.localizedMessage}")
                    }
                }

                // 获取特定推送下的所有评论
                get("/") {
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val comments = commentService.getCommentsByTargetId(postId.toString(), CommentTargetType.POST)
                        call.respond(HttpStatusCode.OK, comments)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "获取评论时发生错误: ${e.localizedMessage}")
                    }
                }

                // 删除特定推送下的特定评论
                delete("/{commentId}") {
                    val principal = call.principal<JWTPrincipal>()
                    val userId = principal?.payload?.getClaim("userId")?.asString()
                    val role = principal?.payload?.getClaim("role")?.asString()
                    val commentId = call.parameters["commentId"] ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的评论ID")

                    try {
                        val comment = commentService.findCommentById(commentId)
                        if (comment != null && (comment.userId == userId || role == Role.COMMUNITY_ADMIN.name || role == Role.SUPER_ADMIN.name)) {
                            if (commentService.deleteComment(commentId)) {
                                call.respond(HttpStatusCode.OK, "评论已删除")
                            } else {
                                call.respond(HttpStatusCode.InternalServerError, "删除评论时发生错误")
                            }
                        } else {
                            call.respond(HttpStatusCode.Forbidden, "你没有权限删除此评论")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "删除评论时发生错误: ${e.localizedMessage}")
                    }
                }
            }
        }

        authenticate {
            route("/community/posts/{postId}") {
                // 点赞特定推送
                post("/like") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val userId = call.receive<String>()
                        val userLike = likeService.addLike(userId, postId.toString(), LikeTargetType.POST)
                        call.respond(HttpStatusCode.Created, userLike)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "点赞时发生错误: ${e.localizedMessage}")
                    }
                }

                // 取消点赞
                delete("/like") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@delete
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val userId = call.receive<String>()
                        if (likeService.removeLike(userId, postId.toString(), LikeTargetType.POST)) {
                            call.respond(HttpStatusCode.OK, "点赞已取消")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "未找到点赞记录")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "取消点赞时发生错误: ${e.localizedMessage}")
                    }
                }

                // 收藏特定推送
                post("/favorite") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@post
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@post call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val userId = call.receive<String>()
                        val userFavorite = favoriteService.addFavorite(userId, postId.toString(), FavoriteTargetType.POST)
                        call.respond(HttpStatusCode.Created, userFavorite)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "收藏时发生错误: ${e.localizedMessage}")
                    }
                }

                // 删除对特定推送的收藏
                delete("/favorite") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@delete
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@delete call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val userId = call.receive<String>()
                        if (favoriteService.removeFavorite(userId, postId.toString(), FavoriteTargetType.POST)) {
                            call.respond(HttpStatusCode.OK, "收藏已取消")
                        } else {
                            call.respond(HttpStatusCode.NotFound, "未找到收藏记录")
                        }
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "取消收藏时发生错误: ${e.localizedMessage}")
                    }
                }

                // 获取特定推送的点赞量，评论量，收藏量
                get("/stats") {
                    val principal = call.principal<JWTPrincipal>()
                    if (principal == null || !principal.hasRole(Role.USER)) {
                        call.respond(HttpStatusCode.Forbidden, "你没有访问权限")
                        return@get
                    }
                    val postId = call.parameters["postId"]?.let { UUID.fromString(it) }
                        ?: return@get call.respond(HttpStatusCode.BadRequest, "无效的推送ID")

                    try {
                        val stats = mapOf(
                            "likes" to likeService.countLikes(postId.toString(), LikeTargetType.POST),
                            "comments" to commentService.getCommentsByTargetId(postId.toString(), CommentTargetType.POST).size,
                            "favorites" to favoriteService.getFavoritesByUserId(postId.toString()).size
                        )
                        call.respond(HttpStatusCode.OK, stats)
                    } catch (e: Exception) {
                        call.respond(HttpStatusCode.InternalServerError, "获取推送统计信息时发生错误: ${e.localizedMessage}")
                    }
                }
            }
        }
    }
}