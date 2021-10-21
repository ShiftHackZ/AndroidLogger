package com.shz.logger

import android.util.Log

fun interface LoggerPrinter {
    fun log(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    )
}

class AdvancedLoggerPrinter : LoggerPrinter {

    override fun log(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) {
        throwable?.let { t ->
            when (loggerType) {
                LoggerType.VERBOSE -> Log.v(tag, "${formatPrefix(prefix)}$content", t)
                LoggerType.DEBUG -> Log.d(tag, "${formatPrefix(prefix)}$content", t)
                LoggerType.INFO -> Log.i(tag, "${formatPrefix(prefix)}$content", t)
                LoggerType.WARNING -> Log.w(tag, "${formatPrefix(prefix)}$content", t)
                LoggerType.EXCEPTION -> Log.e(tag, "${formatPrefix(prefix)}$content", t)
            }
        } ?: run {
            when (loggerType) {
                LoggerType.VERBOSE -> Log.v(tag, "${formatPrefix(prefix)}$content")
                LoggerType.DEBUG -> Log.d(tag, "${formatPrefix(prefix)}$content")
                LoggerType.INFO -> Log.i(tag, "${formatPrefix(prefix)}$content")
                LoggerType.WARNING -> Log.w(tag, "${formatPrefix(prefix)}$content")
                LoggerType.EXCEPTION -> Log.e(tag, "${formatPrefix(prefix)}$content")
            }
        }
    }

    private fun formatPrefix(prefix: String?): String = prefix
        ?.takeIf { it.isNotEmpty() }
        ?.let { "[$prefix] " } ?: ""
}