package com.shz.logger.middleware

import com.shz.logger.TAG

/**
 * Manager that contains whitelisted middleware that can't be removed during runtime.
 */
internal object LoggerWhitelistedMiddleware {

    /**
     * Part of com.shz.logger:logger-kit
     *
     * Allows to write logs in local sqlite database.
     */
    private const val LOGGER_KIT_DATABASE_LOGGER_MIDDLEWARE = "DatabaseLoggerMiddleware"

    /**
     * Local static list of whitelisted middlewares.
     */
    private val whitelistedMiddlewares = listOf(
        LOGGER_KIT_DATABASE_LOGGER_MIDDLEWARE,
    )

    /**
     * Allows to check if instance of [LoggerMiddleware] is whitelisted.
     *
     * @param middleware instance of middleware to check;
     *
     * @return logical flag (true/false) if middleware is whitelisted.
     */
    fun isWhitelisted(middleware: LoggerMiddleware): Boolean = when {
        whitelistedMiddlewares.contains(middleware::class.TAG) -> true
        else -> false
    }
}