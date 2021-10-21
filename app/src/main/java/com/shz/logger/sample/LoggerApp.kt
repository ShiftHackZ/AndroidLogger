package com.shz.logger.sample

import android.app.Application
import com.shz.logger.Logger
import com.shz.logger.middleware.LoggerNetworkMiddleware

class LoggerApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.addMiddleware(LoggerNetworkMiddleware(this))
    }
}