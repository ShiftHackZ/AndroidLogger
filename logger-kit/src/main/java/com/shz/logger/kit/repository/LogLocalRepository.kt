package com.shz.logger.kit.repository

import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.presentation.filter.LogFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogLocalRepository {

    suspend fun queryLogs(filter: LogFilter) = withContext(Dispatchers.IO) {
        LoggerKit.getDatabase().logDao().query(mapFilterToQuery(filter))
    }
}