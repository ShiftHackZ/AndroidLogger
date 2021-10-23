package com.shz.logger.sample

import android.app.Application
import com.shz.logger.Logger
import com.shz.logger.kit.LoggerKit
import com.shz.logger.middleware.LoggerNetworkMiddleware

class LoggerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        LoggerKit.initialize(this)
    }
}