package com.shz.logger.kit.presentation.viewer

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.shz.logger.kit.R
import com.shz.logger.kit.base.BaseLoggerKitActivity
import com.shz.logger.kit.databinding.ActivityLogViewerBinding
import com.shz.logger.kit.export.share.LoggerShareUtility
import com.shz.logger.kit.presentation.filter.mapPositionToLogType
import com.shz.logger.kit.utils.*

class LogViewerActivity : BaseLoggerKitActivity<ActivityLogViewerBinding>() {

    override val inflater: (LayoutInflater) -> ActivityLogViewerBinding
        get() = ActivityLogViewerBinding::inflate

    private val logViewerAdapter = LogViewerAdapter()

    private lateinit var viewModel: LogViewerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding.rvLogs.adapter = logViewerAdapter
        viewModel = ViewModelProvider(this).get(LogViewerViewModel::class.java)
        setupFiltersUi()
        setupViewModel()
        viewModel.getLogs()
    }

    private fun setupViewModel() {
        viewModel.logs.observeNotNull(this, logViewerAdapter::submitList)
        viewModel.share.observeNotNull(this) {
            LoggerShareUtility.shareAsFile(this, it)
        }
        viewModel.entryCount.observeNotNull(this) {
            binding.tvStatus.text = getString(R.string.logger_kit_format_entry_count, "$it")
        }
        viewModel.uiFiltersVisibility.observeNotNull(this, binding.filters.root::showcase)
        viewModel.uiFiltersClear.observeNotNull(this) {
            binding.filters.etClass.clear()
            binding.filters.etTag.clear()
            binding.filters.etMessage.clear()
            binding.filters.etSessionId.clear()
        }
        viewModel.uiFiltersData.observeNotNull(this) {
            binding.filters.btnTimestampRange.text = it.timestampRange?.format()
                ?: getString(R.string.logger_kit_filter_placeholder_timestamp_range)
        }
    }

    private fun setupFiltersUi() {
        binding.btnFilter.setOnClickListener { viewModel.onFiltersClick() }
        binding.btnShare.setOnClickListener { viewModel.shareLogs() }
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
            mapPositionToLogType(it).let(viewModel::updateFilterLogType)
        }
        binding.filters.etClass.onChanged(viewModel::updateFilterClassName)
        binding.filters.etTag.onChanged(viewModel::updateFilterTag)
        binding.filters.etMessage.onChanged(viewModel::updateFilterMessage)
        binding.filters.etSessionId.onChanged(viewModel::updateFilterSessionId)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showTimestampDatePickers() {
        showDatePicker { start ->
            showDatePicker { end ->
                viewModel.updateFilterTimestampRange(start.time to end.time)
            }
        }
    }
}