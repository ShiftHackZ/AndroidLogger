package com.shz.logger.kit.middleware

import android.util.Log
import com.shz.logger.LoggerType
import com.shz.logger.TAG
import com.shz.logger.kit.database.dao.LogDao
import com.shz.logger.kit.database.entity.LogEntity
import com.shz.logger.middleware.LoggerMiddleware
import java.util.concurrent.Executors

class DatabaseLoggerMiddleware(
    private val dao: LogDao,
    private val sessionId: String
) : LoggerMiddleware {

    private val executorService = Executors.newFixedThreadPool(EXECUTOR_POOL_SIZE)

    override fun onLogReceived(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) = Unit

    override fun onLogPrinted(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) {
        if (tag == this::class.TAG) return
        executorService.execute {
            dao.insert(
                LogEntity(
                    id = 0L,
                    sessionId = sessionId,
                    timestamp = System.currentTimeMillis(),
                    type = loggerType.toString(),
                    className = tag,
                    prefix = prefix ?: "",
                    message = content,
                    stacktrace = Log.getStackTraceString(throwable)
                )
            )
        }
    }

    companion object {
        private const val EXECUTOR_POOL_SIZE = 4
    }
}