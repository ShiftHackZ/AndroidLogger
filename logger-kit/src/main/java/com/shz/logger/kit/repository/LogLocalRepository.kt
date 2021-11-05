package com.shz.logger.kit.repository

import androidx.lifecycle.LiveData
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.database.LoggerDatabaseProvider
import com.shz.logger.kit.presentation.filter.LogFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogLocalRepository {

    suspend fun queryLogs(filter: LogFilter) = withContext(Dispatchers.IO) {
        LoggerKit.Debugger.print("DB", "Requesting logs with filter: $filter")
        LoggerDatabaseProvider.provide().logDao().query(mapFilterToQuery(filter))
    }

    suspend fun clearDatabase() = withContext(Dispatchers.IO) {
        LoggerKit.Debugger.print("DB", "Clearing database")
        LoggerDatabaseProvider.provide().logDao().clear()
    }

    fun observeLogCount(): LiveData<Int> {
        return LoggerDatabaseProvider.provide().logDao().observeCount()
    }
}