package com.example.services.dataAccessServices.community

import IUserRepository
import com.example.models.transmissionModels.auth.user.User

class UserService(private val userRepository: IUserRepository) : IUserService {

    /**
     * 根据用户ID查找用户信息。
     * @param userId 用户ID
     * @return 用户对象，若未找到则返回 null
     */
    override fun findUserById(userId: String): User? {
        return userRepository.findUserById(userId)
    }

    /**
     * 根据邮箱或手机查找用户信息。
     * @param identifier 邮箱或手机
     * @return 用户对象，若未找到则返回 null
     */
    override fun findUserByEmailOrPhone(identifier: String): User? {
        return userRepository.findUserByEmailOrPhone(identifier)
    }

    /**
     * 更新用户的头像。
     * @param userId 用户ID
     * @param avatarUrl 新头像的URL
     * @return 操作是否成功
     */
    override fun updateUserAvatar(userId: String, avatarUrl: String): Boolean {
        return userRepository.updateUserAvatar(userId, avatarUrl)
    }

    /**
     * 更新用户的个人信息，例如简介、用户名等。
     * @param user 更新后的用户对象，包含需要更新的字段。
     * @return 操作是否成功
     */
    override fun updateUserInfo(user: User): Boolean {
        return userRepository.updateUser(user)
    }

    /**
     * 检查用户是否存在。
     * @param userId 用户ID
     * @return 如果用户存在则返回 true，否则返回 false。
     */
    override fun userExists(userId: String): Boolean {
        return userRepository.findUserById(userId) != null
    }
}
