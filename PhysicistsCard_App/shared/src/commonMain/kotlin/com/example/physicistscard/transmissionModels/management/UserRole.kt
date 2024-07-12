// 定义用户的角色（如管理员、普通用户等）和相应的权限（如编辑帖子、删除评论等）
data class UserRole(
    val roleId: String,
    val name: String,
    val permissions: List<Permission>
)