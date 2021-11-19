package com.shz.logger.kit.export

import com.shz.logger.LoggerType
import com.shz.logger.kit.database.entity.LogEntity
import com.shz.logger.kit.utils.formatTimestamp

internal class LoggerCollectionPrinter {

    fun createLogEntriesOutput(logs: List<LogEntity>): String {
        var result = ""
        logs.forEach { log ->
            result += "${log.timestamp.formatTimestamp()} " +
                    "${mapLoggerTypeToSymbol(LoggerType.valueOf(log.type))}/" +
                    "${log.className}: " +
                    "[${log.prefix}] " +
                    "${log.message}\n"
            log.stacktrace.takeIf { it.isNotEmpty() }?.let { stacktrace ->
                result += "$stacktrace\n"
            }
        }
        return result
    }
}