import com.example.physicistscard.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UserComment(
    val commentId: String,
    val userId: String, // 发表评论的用户ID
    val targetId: String, // 被评论的目标对象ID
    val targetType: CommentTargetType, // 评论的目标类型
    val content: String, // 评论内容
    @Serializable(with = LocalDateTimeSerializer::class)
    val createdAt: LocalDateTime, // 评论时间
    val parentId: String? = null // 父评论ID，用于支持评论的嵌套回复，顶级评论此字段为null
)