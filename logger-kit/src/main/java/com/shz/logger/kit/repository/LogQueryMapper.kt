package com.shz.logger.kit.repository

import androidx.sqlite.db.SimpleSQLiteQuery
import com.shz.logger.kit.database.contract.LogEntityContract
import com.shz.logger.kit.presentation.filter.LogFilter

fun mapFilterToQuery(filter: LogFilter): SimpleSQLiteQuery {
    val order = "ORDER BY ${LogEntityContract.TIMESTAMP} DESC"
    var request = "SELECT * FROM ${LogEntityContract.TABLE_NAME} "
    val args = arrayListOf<Any>()
    filter.loggerType?.let {
        request += "${startCommand(request)} ${LogEntityContract.TYPE} = ? "
        args.add("$it")
    }
    filter.className?.let {
        request += "${startCommand(request)} ${LogEntityContract.CLASS} LIKE ? "
        args.add(it)
    }
    filter.prefix?.let {
        request += "${startCommand(request)} ${LogEntityContract.PREFIX} LIKE ? "
        args.add(it)
    }
    filter.message?.let {
        request += "${startCommand(request)} ${LogEntityContract.MESSAGE} LIKE ? "
        args.add(it)
    }
    filter.sessionId?.let {
        request += "${startCommand(request)} ${LogEntityContract.SESSION_ID} LIKE ? "
        args.add(it)
    }
    request = request.trim()
    return SimpleSQLiteQuery("$request $order", args.toArray())
}

private fun startCommand(request: String): String = when {
    request.contains("WHERE") -> "AND"
    else -> "WHERE"
}
