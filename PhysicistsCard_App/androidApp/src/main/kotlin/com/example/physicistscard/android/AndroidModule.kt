package com.example.physicistscard.android

import AuthViewModel
import com.example.physicistscard.android.viewModels.ChatViewModel
import com.example.physicistscard.android.viewModels.CommunityViewModel
import com.example.physicistscard.android.viewModels.FriendshipViewModel
import com.example.physicistscard.android.viewModels.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    // ViewModel 依赖项
    viewModel { AuthViewModel(get()) }
    viewModel { CommunityViewModel(get()) }
    viewModel { FriendshipViewModel(get()) }
    viewModel { ChatViewModel(get()) }
    viewModel { UserViewModel(get()) }
}