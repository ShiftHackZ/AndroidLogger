package com.shz.logger.kit.presentation.filter

import com.shz.logger.LoggerType

fun mapPositionToLogType(position: Int): LoggerType? = when (position) {
    0 -> null
    1 -> LoggerType.VERBOSE
    2 -> LoggerType.DEBUG
    3 -> LoggerType.INFO
    4 -> LoggerType.WARNING
    5 -> LoggerType.EXCEPTION
    else -> null
}