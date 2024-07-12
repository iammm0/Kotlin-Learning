data class PermissionGroup(
    val groupId: String,
    val name: String,
    val description: String?,
    val permissions: List<Permission> // 此处为简化表示，实际可能需要通过关联表实现
)