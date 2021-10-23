package com.shz.logger.kit.export

import com.shz.logger.LoggerType

fun mapLoggerTypeToSymbol(type: LoggerType): String = when (type) {
    LoggerType.VERBOSE -> "V"
    LoggerType.DEBUG -> "D"
    LoggerType.INFO -> "I"
    LoggerType.WARNING -> "W"
    LoggerType.EXCEPTION -> "E"
}