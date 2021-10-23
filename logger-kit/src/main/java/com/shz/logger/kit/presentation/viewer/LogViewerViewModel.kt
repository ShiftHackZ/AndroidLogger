package com.shz.logger.kit.presentation.viewer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shz.logger.LoggerType
import com.shz.logger.kit.database.entity.LogEntity
import com.shz.logger.kit.presentation.filter.LogFilter
import com.shz.logger.kit.repository.LogLocalRepository
import com.shz.logger.kit.utils.LoggerLiveEvent
import com.shz.logger.kit.utils.rearrange
import kotlinx.coroutines.launch

class LogViewerViewModel : ViewModel() {

    val logs = LoggerLiveEvent<List<LogEntity>>()
    val share = LoggerLiveEvent<List<LogEntity>>()
    val entryCount = LoggerLiveEvent<Int>()

    val uiFiltersVisibility = LoggerLiveEvent<Boolean>()
    val uiFiltersData = LoggerLiveEvent<LogFilter>()
    val uiFiltersClear = LoggerLiveEvent<Unit>()

    private val repository = LogLocalRepository()
    private var filter: LogFilter = LogFilter()

    init {
        uiFiltersVisibility.value = false
        uiFiltersData.value = filter
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

    fun onFiltersClick() {
        uiFiltersVisibility.value?.let { uiFiltersVisibility.value = !it }
    }

    fun updateFilterTimestampRange(range: Pair<Long, Long>) {
        filter = filter.copy(timestampRange = range.rearrange())
        uiFiltersData.value = filter
        //getLogs()
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