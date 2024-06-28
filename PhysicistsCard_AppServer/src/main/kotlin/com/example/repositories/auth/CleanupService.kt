package com.example.repositories.auth

import kotlinx.coroutines.*

class CleanupService(private val repository: VerificationCodeRepository) {
    private val job = Job()
    private val scope = CoroutineScope(job + Dispatchers.Default)

    @OptIn(DelicateCoroutinesApi::class)
    fun startCleanupJob() = GlobalScope.launch {
        while (isActive) { // 保持这个协程活跃
            try {
                if (repository.deleteExpiredVerificationCodes()) {
                    println("成功删除过期或已使用的验证码。")
                } else {
                    println("没有需要删除的过期或已使用的验证码。")
                }
            } catch (e: Exception) {
                println("清理过程中发生错误: ${e.message}")
            }
            delay(5 * 60 * 1000L) // 等待一定时间，例如5分钟
        }
    }

    fun stopCleanupJob() {
        job.cancel()
    }
}