package com.shz.logger.kit.presentation.filter

import com.shz.logger.LoggerType

data class LogFilter(
    val sessionId: String? = null,
    val loggerType: LoggerType? = null,
    val timestampRange: Pair<Long, Long>? = null,
    val className: String? = null,
    val prefix: String? = null,
    val message: String? = null
)