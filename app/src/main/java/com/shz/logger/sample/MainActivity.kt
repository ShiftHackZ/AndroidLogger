package com.shz.logger.sample

import android.os.Bundle
import android.view.LayoutInflater
import com.shz.logger.Logger
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.base.BaseLoggerKitActivity
import com.shz.logger.kit.utils.clear
import com.shz.logger.sample.databinding.ActivityMainBinding
import java.lang.RuntimeException

class MainActivity : BaseLoggerKitActivity<ActivityMainBinding>() {

    override val inflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(this::class, "onCreate", "Hello World!")
        binding.btnLogs.setOnClickListener {
            LoggerKit.openLogViewer()
        }
        binding.btnLog.setOnClickListener {
            Logger.d(this::class, binding.etPrefix.text.toString(), binding.etMessage.text.toString())
            binding.etPrefix.clear()
            binding.etMessage.clear()
        }
    }
}