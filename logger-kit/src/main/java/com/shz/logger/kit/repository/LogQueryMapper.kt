package com.shz.logger.kit.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.shz.logger.kit.database.contract.LogEntityContract
import com.shz.logger.kit.presentation.filter.LogFilter

private const val SQL_LIKE_PREDICATE = "LIKE '%' || ? || '%'"

fun mapFilterToQuery(filter: LogFilter): SimpleSQLiteQuery {
    val order = "ORDER BY ${LogEntityContract.TIMESTAMP} DESC"
    var request = "SELECT * FROM ${LogEntityContract.TABLE_NAME} "
    val args = arrayListOf<Any>()
    filter.timestampRange?.let {
        request += "${startCommand(request)} ${LogEntityContract.TIMESTAMP} >= ? "
        args.add("${it.first}")
        request += "${startCommand(request)} ${LogEntityContract.TIMESTAMP} <= ? "
        args.add("${it.second}")
    }
    filter.loggerType?.let {
        request += "${startCommand(request)} ${LogEntityContract.TYPE} = ? "
        args.add("$it")
    }
    filter.className?.let {
        request += "${startCommand(request)} ${LogEntityContract.CLASS} $SQL_LIKE_PREDICATE "
        args.add(it)
    }
    filter.prefix?.let {
        request += "${startCommand(request)} ${LogEntityContract.PREFIX} $SQL_LIKE_PREDICATE "
        args.add(it)
    }
    filter.message?.let {
        request += "${startCommand(request)} ${LogEntityContract.MESSAGE} $SQL_LIKE_PREDICATE "
        args.add(it)
    }
    filter.sessionId?.let {
        request += "${startCommand(request)} ${LogEntityContract.SESSION_ID} $SQL_LIKE_PREDICATE "
        args.add(it)
    }
    request = request.trim()
    return SimpleSQLiteQuery("$request $order", args.toArray())
}

private fun startCommand(request: String): String = when {
    request.contains("WHERE") -> "AND"
    else -> "WHERE"
}
