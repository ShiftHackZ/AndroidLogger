package com.shz.logger.printer

import com.shz.logger.LoggerType
import com.shz.logger.Logger

/**
 * Printer contract interface.
 * Used to proceed with output of log messages.
 *
 * @see Logger
 */
fun interface LoggerPrinter {

    /**
     * Method which receives log message to perform output.
     *
     * @param loggerType type of log message;
     * @param tag class name;
     * @param content log message;
     * @param prefix some tag that is used for grouping log messages;
     * @param throwable exception represented as [Throwable];
     */
    fun log(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    )
}