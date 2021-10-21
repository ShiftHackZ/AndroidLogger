package com.shz.logger.middleware

import com.shz.logger.LoggerType

class LoggerNetworkMiddleware(

) : LoggerMiddleware {

    override fun onLogReceived(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) {

    }

    override fun onLogPrinted(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) {

    }
}