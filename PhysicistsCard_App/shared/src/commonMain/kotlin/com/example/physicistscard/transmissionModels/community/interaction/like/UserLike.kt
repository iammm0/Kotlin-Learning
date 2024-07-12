import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

// 用户互动模型记录了用户对帖子和评论的点赞、收藏等行为
// 对于用户收藏、评论、点赞等社区互动功能，需要确保有相应的模型（如UserInteraction）来记录这些行为，并且能够支持快速查询和管理
@Serializable
data class UserLike(
    val likeId: String,
    val userId: String, // 进行点赞的用户ID
    val targetId: String, // 被点赞的目标对象ID，可以是帖子或商品ID
    val targetType: LikeTargetType, // 明确点赞的目标类型，有助于区分是帖子还是商品
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime // 点赞发生的时间，便于分析用户行为模式
)