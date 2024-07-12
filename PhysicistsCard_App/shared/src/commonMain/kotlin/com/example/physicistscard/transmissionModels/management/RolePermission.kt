// 虽然UserRole和Permission已经为角色和权限管理提供了基础，但在实际应用中，角色与权限之间可能存在多对多的关系。因此，可以通过角色-权限关联表来实现更灵活的权限管理。
data class RolePermission(
    val roleId: String,
    val permissionId: String
)