package com.shz.logger.sample.presentation

import android.os.Bundle
import android.view.LayoutInflater
import com.shz.logger.Logger
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.utils.clear
import com.shz.logger.sample.base.BaseActivity
import com.shz.logger.sample.databinding.ActivityMainBinding
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val inflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private var testLogListenable = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(this::class, "onCreate", "Hello World!")
        setupUi()
        liveUpdates()
    }

    private fun setupUi() = with(binding) {
        btnLogs.setOnClickListener {
            LoggerKit.openLogViewer()
        }
        btnMainActLogs.setOnClickListener {
            LoggerKit.openLogViewer(MainActivity::class)
        }
        btnLog.setOnClickListener {
            Logger.d(this::class, etPrefix.text.toString(), etMessage.text.toString())
            etPrefix.clear()
            etMessage.clear()
        }
        swLogTimer.setOnCheckedChangeListener { _, isChecked ->
            testLogListenable = isChecked
        }
    }

    private fun liveUpdates() {
        Observable.interval(LIVE_PERIOD, TimeUnit.SECONDS)
            .flatMapSingle { Single.just(it to testLogListenable) }
            .map { (index, enabled) ->
                val prefix = when {
                    binding.etPrefix.text.toString()
                        .isNotEmpty() -> binding.etPrefix.text.toString()
                    else -> LIVE_PREFIX
                }
                val message = when {
                    binding.etMessage.text.toString()
                        .isNotEmpty() -> binding.etMessage.text.toString()
                    else -> "Hello from | $index | ${System.currentTimeMillis()}"
                }
                if (enabled) Logger.d(this::class, prefix, message)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    companion object {
        private const val LIVE_PREFIX = "Live"
        private const val LIVE_PERIOD = 5L
    }
}