package com.shz.logger.kit.presentation.viewer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.shz.logger.Logger
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.R
import com.shz.logger.kit.base.BaseLoggerKitActivity
import com.shz.logger.kit.databinding.ActivityLogViewerBinding
import com.shz.logger.kit.export.share.LoggerShareUtility
import com.shz.logger.kit.presentation.filter.LogFilter
import com.shz.logger.kit.presentation.filter.mapPositionToLogType
import com.shz.logger.kit.utils.*

class LogViewerActivity : BaseLoggerKitActivity<ActivityLogViewerBinding>() {

    override val inflater: (LayoutInflater) -> ActivityLogViewerBinding
        get() = ActivityLogViewerBinding::inflate

    private val logViewerAdapter = LogViewerAdapter()

    private val liveLogsObserver: Observer<Int> = Observer {
        if (performLiveUpdates) viewModel.getLogs()
    }

    private var performLiveUpdates = false

    private var performScrollToTop = false

    private lateinit var viewModel: LogViewerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.statusBarColor = ContextCompat.getColor(this, R.color.colorLoggerKitBackground)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            WindowInsetsControllerCompat(window, window.decorView).apply {
                isAppearanceLightStatusBars = true
            }
        }
        supportActionBar?.hide()
        binding.rvLogs.adapter = logViewerAdapter
        viewModel = ViewModelProvider(this).get(LogViewerViewModel::class.java)
        setupFiltersUi()
        setupSettingsUi()
        setupViewModel()
        startLogUpdates()
        viewModel.getLogs()
        LoggerKit.Debugger.print("UI", "LoggerKit viewer started")
    }

    override fun onBackPressed() {
        viewModel.uiSettingsVisibility.value?.let { settingsVisible ->
            when (settingsVisible) {
                true -> viewModel.uiSettingsVisibility.value = false
                else ->  super.onBackPressed()
            }
        } ?: run {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        stopLogUpdates()
        super.onDestroy()
    }

    @SuppressLint("StringFormatMatches")
    private fun setupViewModel() {
        viewModel.logs.observeNotNull(this) {
            logViewerAdapter.submitList(it)
            if (performScrollToTop) runOnUiThread {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.rvLogs.layoutManager?.scrollToPosition(0)
                }, 250L)
            }
        }
        viewModel.share.observeNotNull(this) {
            LoggerKit.Debugger.print("SHARE", "Trying to share ${it.size} entries")
            LoggerShareUtility.shareAsFile(this, it)
        }
        viewModel.entryCount.observeNotNull(this) {
            LoggerKit.Debugger.print("UI", "Entry count changed: $it")
            binding.emptyState.root.showcase(it == 0)
            binding.tvStatus.text = getString(R.string.logger_kit_format_entry_count, "$it")
        }
        viewModel.uiFiltersVisibility.observeNotNull(this) {
            LoggerKit.Debugger.print("UI", "Filters visibility changed state: $it")
            binding.filters.root.showcase(it)
        }
        viewModel.uiFiltersClear.observeNotNull(this) {
            LoggerKit.Debugger.print("UI", "Clearing all filters")
            binding.filters.spinnerLogType.setSelection(0)
            binding.filters.etClass.clear()
            binding.filters.etTag.clear()
            binding.filters.etMessage.clear()
            binding.filters.etSessionId.clear()
        }
        viewModel.uiFiltersData.observeNotNull(this) {
            binding.ivFilterIndicator.showcase(it.hashCode() != LogFilter().hashCode())
            binding.filters.btnTimestampRange.text = it.timestampRange?.format()
                ?: getString(R.string.logger_kit_filter_placeholder_timestamp_range)
        }
        viewModel.uiSettingsVisibility.observeNotNull(this) {
            LoggerKit.Debugger.print("UI", "Settings visibility changed state: $it")
            binding.settings.root.showcase(it)
        }
        viewModel.uiSettingsStats.observeNotNull(this) {
            binding.settings.tvMiddlewareCount.text = getString(
                R.string.logger_kit_format_middleware_count,
                it.middlewareCount
            )
            binding.settings.tvMiddlewareList.text = it.middlewareNames.toString()
            binding.settings.tvLoggerVersion.text =
                getString(R.string.logger_kit_format_logger_version, Logger.VERSION)
            binding.settings.tvLoggerKitVersion.text =
                getString(R.string.logger_kit_format_logger_kit_version, LoggerKit.VERSION)
            binding.settings.tvLoggerKitDebug.text =
                getString(
                    R.string.logger_kit_format_logger_debug_mode,
                    LoggerKit.Debugger.printDebug
                )
            //binding.settings.swDebugMode.isChecked = LoggerKit.Debugger.printDebug
            binding.settings.tvSessionId.text =
                getString(R.string.logger_kit_format_logger_session_id, LoggerKit.Config.sessionId)
        }
        viewModel.uiScrollToTop.observeNotNull(this) {
            performScrollToTop = it
            binding.settings.swScrollToTop.isChecked = it
        }
        viewModel.uiListenLogUpdates.observeNotNull(this) {
            performLiveUpdates = it
            binding.settings.swLiveUpdates.isChecked = it
            binding.tvLive.showcase(it)
            binding.settings.swScrollToTop.showcase(it)
        }
    }

    private fun setupFiltersUi() {
        binding.btnFilter.setOnClickListener {
            viewModel.onFiltersClick()
            hideKeyboard()
        }
        binding.btnShare.setOnClickListener {
            viewModel.shareLogs()
            hideKeyboard()
        }
        binding.filters.btnTimestampRange.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) showTimestampDatePickers()
        }
        binding.filters.btnClear.setOnClickListener {
            viewModel.clearFilters()
        }
        binding.filters.spinnerLogType.adapter = ArrayAdapter.createFromResource(
            this, R.array.logger_kit_entry_type, android.R.layout.simple_spinner_item
        )
        binding.filters.spinnerLogType.setSelection(0)
        binding.filters.spinnerLogType.onSelected {
            hideKeyboard()
            mapPositionToLogType(it).let(viewModel::updateFilterLogType)
        }
        binding.filters.etClass.onChanged(viewModel::updateFilterClassName)
        binding.filters.etTag.onChanged(viewModel::updateFilterTag)
        binding.filters.etMessage.onChanged(viewModel::updateFilterMessage)
        binding.filters.etSessionId.onChanged(viewModel::updateFilterSessionId)
    }

    private fun setupSettingsUi() {
        binding.btnSettings.setOnClickListener {
            viewModel.onSettingsClick()
            viewModel.loadStats()
        }
        binding.settings.swLiveUpdates.setOnCheckedChangeListener { _, isChecked ->
            viewModel.uiListenLogUpdates.value = isChecked
        }
        binding.settings.swScrollToTop.setOnCheckedChangeListener { _, isChecked ->
            viewModel.uiScrollToTop.value = isChecked
        }
        binding.settings.swDebugMode.setOnCheckedChangeListener { _, isChecked ->
            LoggerKit.Debugger.printDebug = isChecked
            viewModel.loadStats()
        }
        binding.settings.btnSessionId.setOnClickListener {
            LoggerKit.generateSessionId()
            viewModel.loadStats()
        }
        binding.settings.btnDatabaseClear.setOnClickListener {
            viewModel.clearLoggerDatabase()
        }
    }

    private fun startLogUpdates() {
        if (!viewModel.eventLogsUpdated.hasActiveObservers()) {
            viewModel.eventLogsUpdated.observeForever(liveLogsObserver)
        }
    }

    private fun stopLogUpdates() {
        if (viewModel.eventLogsUpdated.hasActiveObservers()) {
            viewModel.eventLogsUpdated.removeObserver(liveLogsObserver)
        }
        viewModel.eventLogsUpdated.removeObservers(this)
        viewModel.uiScrollToTop.value = false
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showTimestampDatePickers() {
        hideKeyboard()
        LoggerKit.Debugger.print("UI", "Started filter date picker")
        showDatePicker { start ->
            LoggerKit.Debugger.print("UI", "Range #1: ${start.time}")
            showDatePicker { end ->
                LoggerKit.Debugger.print("UI", "Range #2: ${end.time}")
                viewModel.updateFilterTimestampRange(start.time to end.time)
            }
        }
    }
}