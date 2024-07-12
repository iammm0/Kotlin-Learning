package com.example.repositories.auth

import kotlinx.coroutines.*

class TokenCleanupService(private val repository: TokenRepository) {
    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Default)

    @OptIn(DelicateCoroutinesApi::class)
    fun startCleanupJob() = GlobalScope.launch {
        while (isActive) {
            try {
                if (repository.deleteExpiredTokens()) {
                    println("成功删除过期令牌。")
                } else {
                    println("没有需要删除的过期令牌。")
                }
            } catch (e: Exception) {
                println("清理过程中发生错误: ${e.message}")
            }
            delay(5 * 60 * 1000L) // 每5分钟检查一次
        }
    }

    fun stopCleanupJob() {
        job.cancel()
    }
}
