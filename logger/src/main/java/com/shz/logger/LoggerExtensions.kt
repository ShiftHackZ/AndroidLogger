package com.shz.logger

import com.shz.logger.middleware.LoggerMiddleware
import com.shz.logger.middleware.LoggerWhitelistedMiddleware
import kotlin.reflect.KClass

val Class<*>.TAG: String
    get() = this.simpleName

val KClass<*>.TAG: String
    get() = this.simpleName.toString()

val LoggerMiddleware.isWhitelisted: Boolean
    get() = LoggerWhitelistedMiddleware.isWhitelisted(this)