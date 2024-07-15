package com.example.physicistscard.utils

import com.example.api.impl.AuthApiImpl
import com.example.physicistscard.apiServices.AuthApi
import com.example.physicistscard.businessLogic.AuthService
import com.example.physicistscard.businessLogic.IAuthService
import createCommonHttpClient
import org.koin.core.module.Module
import org.koin.dsl.module

val commonModule: Module = module {

    // 注入 AuthApi 实现
    single<AuthApi> { AuthApiImpl(createCommonHttpClient(), "http://192.168.1.101:8080") } // 替换为你的实际实现

    // 注入 AuthService
    single<IAuthService> { AuthService(get()) }

}
