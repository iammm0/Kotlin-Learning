import kotlinx.serialization.Serializable

@Serializable
enum class CommentTargetType {
    POST, // 帖子
    PRODUCT // 商品
}