package com.shz.logger.kit.presentation.viewer

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shz.logger.LoggerType
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.database.LoggerDatabaseProvider
import com.shz.logger.kit.database.entity.LogEntity
import com.shz.logger.kit.presentation.filter.LogFilter
import com.shz.logger.kit.presentation.filter.LogStats
import com.shz.logger.kit.repository.LogLocalRepository
import com.shz.logger.kit.utils.LoggerLiveEvent
import com.shz.logger.kit.utils.rearrange
import kotlinx.coroutines.launch

class LogViewerViewModel : ViewModel() {

    val logs = LoggerLiveEvent<List<LogEntity>>()
    val share = LoggerLiveEvent<List<LogEntity>>()
    val entryCount = LoggerLiveEvent<Int>()

    val uiSettingsVisibility = LoggerLiveEvent<Boolean>()
    val uiSettingsStats = LoggerLiveEvent<LogStats>()
    val uiFiltersVisibility = LoggerLiveEvent<Boolean>()
    val uiFiltersData = LoggerLiveEvent<LogFilter>()
    val uiFiltersClear = LoggerLiveEvent<Unit>()
    val uiListenLogUpdates = LoggerLiveEvent<Boolean>()
    val uiScrollToTop = LoggerLiveEvent<Boolean>()

    val eventLogsUpdated: LiveData<Int>
        get() = repository.observeLogCount()

    private val repository = LogLocalRepository()
    private var filter: LogFilter = LogFilter()

    init {
        uiSettingsVisibility.value = false
        uiFiltersVisibility.value = false
        uiFiltersData.value = filter
        uiListenLogUpdates.value = false
        uiScrollToTop.value = false
        entryCount.value = 0
        LoggerKit.Debugger.print("UI", "ViewModel initialization successful")
    }

    fun loadStats() {
        uiSettingsStats.value = LogStats()
    }

    fun getLogs() {
        viewModelScope.launch {
            repository.queryLogs(filter).let {
                logs.value = ArrayList(it)
                entryCount.value = it.size
            }
        }
    }

    fun shareLogs() {
        viewModelScope.launch {
            repository.queryLogs(filter).let {
                share.value = it
            }
        }
    }

    fun clearLoggerDatabase() {
        viewModelScope.launch {
            repository.clearDatabase()
            logs.value = arrayListOf()
            entryCount.value = 0
        }
    }

    fun onFiltersClick() {
        uiFiltersVisibility.value?.let { uiFiltersVisibility.value = !it }
    }

    fun onSettingsClick() {
        uiSettingsVisibility.value?.let { uiSettingsVisibility.value = !it }
    }

    fun updateFilterTimestampRange(range: Pair<Long, Long>) {
        filter = filter.copy(timestampRange = range.rearrange())
        uiFiltersData.value = filter
        getLogs()
    }

    fun updateFilterLogType(type: LoggerType?) {
        filter = filter.copy(loggerType = type)
        uiFiltersData.value = filter
        getLogs()
    }

    fun updateFilterClassName(name: String) {
        filter = filter.copy(className = name.takeIf { it.isNotEmpty() })
        uiFiltersData.value = filter
        getLogs()
    }

    fun updateFilterTag(tag: String) {
        filter = filter.copy(prefix = tag.takeIf { it.isNotEmpty() })
        uiFiltersData.value = filter
        getLogs()
    }

    fun updateFilterMessage(message: String) {
        filter = filter.copy(message = message.takeIf { it.isNotEmpty() })
        uiFiltersData.value = filter
        getLogs()
    }

    fun updateFilterSessionId(id: String) {
        filter = filter.copy(sessionId = id.trim().takeIf { it.isNotEmpty() })
        uiFiltersData.value = filter
        getLogs()
    }

    fun clearFilters() {
        filter = LogFilter()
        uiFiltersData.value = filter
        uiFiltersClear.value = Unit
        getLogs()
    }
}