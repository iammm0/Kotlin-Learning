import com.example.models.transmissionModels.community.CommentTargetType
import com.example.models.transmissionModels.community.UserComment
import com.example.repositories.Repository

// 不仅支持基本的CRUD操作，还提供了特定的方法来查询特定目标的评论以及评论的回复
interface IUserCommentRepository : Repository<UserComment,String> {
    fun findByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment>
    // 根据目标ID和类型查找评论，用于展示特定帖子或商品下的所有评论。

    fun findReplies(commentId: String): List<UserComment>
    // 查找特定评论的所有回复，支持评论的嵌套展示。
}

