package com.example.physicistscard.android

import android.app.Application
import com.example.physicistscard.utils.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PhysicistsCardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 启动 Koin
        startKoin {
            // 提供 Android 上下文
            androidContext(this@PhysicistsCardApplication)
            // 加载模块
            modules(sharedModule, androidModule)
        }
    }
}