package com.shz.logger.middleware

import com.shz.logger.LoggerType

interface LoggerMiddleware {
    fun onLogReceived(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    )

    fun onLogPrinted(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    )
}