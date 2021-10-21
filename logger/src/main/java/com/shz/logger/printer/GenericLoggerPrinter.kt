package com.shz.logger.printer

import com.shz.logger.LoggerType

class GenericLoggerPrinter : LoggerPrinter {

    override fun log(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) {
        println("$tag: ${formatPrefix(prefix)}$content")
        throwable?.printStackTrace()
    }

    private fun formatPrefix(prefix: String?): String = prefix
        ?.takeIf { it.isNotEmpty() }
        ?.let { "[$prefix] " } ?: ""
}