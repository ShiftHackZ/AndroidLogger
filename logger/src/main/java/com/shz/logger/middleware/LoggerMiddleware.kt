package com.shz.logger.middleware

import com.shz.logger.LoggerType
import com.shz.logger.Logger
import com.shz.logger.printer.LoggerPrinter

/**
 * Middleware contract interface.
 * Used to create custom logic for log pre/post processing and use with [Logger].
 *
 * @see Logger
 * @see LoggerPrinter
 */
interface LoggerMiddleware {

    /**
     * Triggered BEFORE the log message is printed in [LoggerPrinter].
     *
     * @see LoggerType
     *
     * @param loggerType type of log message;
     * @param tag class name;
     * @param content log message;
     * @param prefix some tag that is used for grouping log messages;
     * @param throwable exception represented as [Throwable];
     */
    fun onLogReceived(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    )

    /**
     * Triggered AFTER the log message is printed in [LoggerPrinter].
     *
     * @see LoggerType
     *
     * @param loggerType type of log message;
     * @param tag class name;
     * @param content log message;
     * @param prefix some tag that is used for grouping log messages;
     * @param throwable exception represented as [Throwable];
     */
    fun onLogPrinted(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    )
}