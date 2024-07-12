import com.example.models.transmissionModels.community.interaction.CommentTargetType
import com.example.models.transmissionModels.community.interaction.UserComment
import com.example.repositories.Repository

// 不仅支持基本的CRUD操作，还提供了特定的方法来查询特定目标的评论以及评论的回复
interface IUserCommentRepository : Repository<UserComment, String> {
    /**
     * 根据目标ID和类型查找评论
     *
     * @param targetId 目标ID
     * @param targetType 目标类型（如帖子或商品）
     * @return 评论列表
     */
    fun findByTargetId(targetId: String, targetType: CommentTargetType): List<UserComment>

    /**
     * 查找特定评论的所有回复
     *
     * @param commentId 评论ID
     * @return 回复列表
     */
    fun findReplies(commentId: String): List<UserComment>
}


