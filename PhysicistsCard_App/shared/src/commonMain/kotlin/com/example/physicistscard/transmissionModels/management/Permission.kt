data class Permission(
    val permissionId: String,
    val name: String, // 权限名称
    val description: String?, // 权限描述
    val category: PermissionCategory // 新增：权限类别，用于更好地组织权限
)