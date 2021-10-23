package com.shz.logger.sample

import android.app.Application
import com.shz.logger.kit.LoggerKit

class LoggerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerKit.setDebugMode(true).initialize(this)
    }
}