package com.shz.logger.kit.repository

import com.shz.logger.kit.database.LoggerDatabaseProvider
import com.shz.logger.kit.presentation.filter.LogFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogLocalRepository {

    suspend fun queryLogs(filter: LogFilter) = withContext(Dispatchers.IO) {
        LoggerDatabaseProvider.provide().logDao().query(mapFilterToQuery(filter))
    }
}