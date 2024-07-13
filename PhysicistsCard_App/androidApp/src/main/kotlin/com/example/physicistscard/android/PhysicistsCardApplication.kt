package com.example.physicistscard.android

import android.app.Application
import com.example.physicistscard.utils.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PhysicistsCardApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PhysicistsCardApplication)
            modules(commonModule)
        }
    }
}