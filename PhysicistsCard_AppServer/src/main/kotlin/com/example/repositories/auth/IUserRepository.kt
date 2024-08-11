import com.example.models.transmissionModels.auth.user.Role
import com.example.models.transmissionModels.auth.user.User

interface IUserRepository {
    /**
     * 创建用户，并返回创建的用户对象
     * @param user 用户对象
     * @return 创建的用户对象
     */
    fun createUser(user: User): User

    /**
     * 创建用户，并返回操作是否成功的布尔值
     * @param user 用户对象
     * @return 操作是否成功
     */
    fun createUserAndReturnUser(user: User): Boolean

    /**
     * 更新用户的邮箱或手机
     * @param userId 用户ID
     * @param email 新邮箱，可为空
     * @param phone 新手机，可为空
     * @return 操作是否成功
     */
    fun updateUserEmailOrPhone(userId: String, email: String?, phone: String?): Boolean

    /**
     * 根据邮箱或手机查找用户
     * @param identifier 邮箱或手机
     * @return 用户对象，若未找到则返回 null
     */
    fun findUserByEmailOrPhone(identifier: String): User?

    /**
     * 重置用户密码
     * @param userId 用户ID
     * @param newPasswordHash 新密码的哈希值
     * @return 操作是否成功
     */
    fun resetUserPassword(userId: String, newPasswordHash: String): Boolean

    /**
     * 更新用户角色
     * @param userId 用户ID
     * @param role 新角色
     * @return 操作是否成功
     */
    fun updateUserRole(userId: String, role: Role): Boolean

    /**
     * 删除用户的所有令牌
     * @param userId 用户ID
     * @return 操作是否成功
     */
    fun deleteTokensByUserId(userId: String): Boolean

    /**
     * 更新用户头像
     * @param userId 用户ID
     * @param avatarUrl 新头像的URL
     * @return 操作是否成功
     */
    fun updateUserAvatar(userId: String, avatarUrl: String): Boolean

    /**
     * 更新用户手机
     * @param userId 用户ID
     * @param phone 新手机
     * @return 操作是否成功
     */
    fun updateUserPhone(userId: String, phone: String): Boolean

    /**
     * 更新用户邮箱
     * @param userId 用户ID
     * @param email 新邮箱
     * @return 操作是否成功
     */
    fun updateUserEmail(userId: String, email: String): Boolean

    /**
     * 检查邮箱或手机是否重复
     * @param email 邮箱
     * @param phone 手机
     * @return 是否重复
     */
    fun checkDuplicateEmailOrPhone(email: String?, phone: String?): Boolean

    /**
     * 根据用户ID查找用户
     * @param userId 用户ID
     * @return 用户对象，若未找到则返回 null
     */
    fun findUserById(userId: String): User?

    /**
     * 更新用户信息。
     *
     * @param user 更新后的用户对象，包含需要更新的字段。
     * @return 更新是否成功。
     */
    fun updateUser(user: User): Boolean
}
