import com.example.models.transmissionModels.auth.tokens.AuthToken
import com.example.models.transmissionModels.auth.user.User
import java.time.LocalDateTime

interface IAuthTokenRepository {
    /**
     * 创建认证令牌
     * @param user 用户对象
     * @param token 令牌字符串
     * @param expiryDate 令牌过期时间
     * @param issuedAt 令牌签发时间
     * @return 操作是否成功
     */
    fun createToken(user: User, token: String, expiryDate: LocalDateTime, issuedAt: LocalDateTime): Boolean

    /**
     * 查找认证令牌
     * @param token 令牌字符串
     * @return 找到的认证令牌对象，若未找到则返回 null
     */
    fun findToken(token: String): AuthToken?

    /**
     * 删除认证令牌
     * @param token 令牌字符串
     * @return 操作是否成功
     */
    fun deleteToken(token: String): Boolean

    /**
     * 删除已过期的认证令牌
     * @return 操作是否成功
     */
    fun deleteExpiredTokens(): Boolean

    /**
     * 根据用户ID删除认证令牌
     * @param userId 用户ID
     * @return 操作是否成功
     */
    fun deleteTokensByUserId(userId: String): Boolean
}
