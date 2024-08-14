package com.example.physicistscard.utils

import AuthApiService
import com.example.physicistscard.apiServices.ChatApiService
import com.example.physicistscard.apiServices.CommunityApiService
import com.example.physicistscard.apiServices.FriendshipApiService
import com.example.physicistscard.apiServices.IAuthApiService
import com.example.physicistscard.apiServices.IChatApiService
import com.example.physicistscard.apiServices.ICommunityApiService
import com.example.physicistscard.apiServices.IFriendshipApiService
import com.example.physicistscard.apiServices.IUserApiService
import com.example.physicistscard.apiServices.UserApiService
import com.example.physicistscard.businessLogic.AuthService
import com.example.physicistscard.businessLogic.ChatService
import com.example.physicistscard.businessLogic.CommunityService
import com.example.physicistscard.businessLogic.FriendshipService
import com.example.physicistscard.businessLogic.IAuthService
import com.example.physicistscard.businessLogic.IChatService
import com.example.physicistscard.businessLogic.ICommunityService
import com.example.physicistscard.businessLogic.IFriendshipService
import com.example.physicistscard.businessLogic.IUserService
import com.example.physicistscard.businessLogic.UserService
import commonHttpClientConfig
import org.koin.dsl.module

val sharedModule = module {
    // 提供网络客户端，注入到API服务中
    single { commonHttpClientConfig() {} }

    // 提供 TokenManager 的单例
    single { TokenManager(get()) }

    // API 服务
    single<IAuthApiService> { AuthApiService(get()) }
    single<ICommunityApiService> { CommunityApiService(get()) }
    single<IChatApiService> { ChatApiService(get()) }
    single<IFriendshipApiService> { FriendshipApiService(get(), get()) }
    single<IUserApiService> { UserApiService(get()) }

    // 服务层
    single<IAuthService> { AuthService(get(), get()) }
    single<ICommunityService> { CommunityService(get(), get()) }
    single<IChatService> { ChatService(get()) }
    single<IFriendshipService> { FriendshipService(get()) }
    single<IUserService> { UserService(get(), get()) }

}
