package com.example.physicistscard.android.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun <T> HandleResult(
    result: Result<T>?,
    onLoading: @Composable () -> Unit = { DefaultLoadingView() },
    onError: @Composable (Throwable) -> Unit = { DefaultErrorView(it) },
    onSuccess: @Composable (T) -> Unit
) {
    when {
        result == null -> onLoading()
        result.isFailure -> onError(result.exceptionOrNull()!!)
        result.isSuccess -> onSuccess(result.getOrNull()!!)
    }
}

@Composable
fun DefaultLoadingView() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun DefaultErrorView(throwable: Throwable) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "发生错误: ${throwable.localizedMessage}",
            color = MaterialTheme.colorScheme.error
        )
    }
}
