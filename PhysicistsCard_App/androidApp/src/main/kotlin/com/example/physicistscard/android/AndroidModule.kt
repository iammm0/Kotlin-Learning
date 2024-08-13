package com.example.physicistscard.android

import AuthViewModel
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.dsl.module

val androidModule = module {
    // ViewModel 依赖项
    viewModel { AuthViewModel(get()) }
    viewModel { CommunityViewModel(get()) }

}