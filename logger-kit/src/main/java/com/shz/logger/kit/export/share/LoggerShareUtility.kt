package com.shz.logger.kit.export.share

import android.content.Context
import android.content.Intent
import androidx.core.content.FileProvider
import com.shz.logger.Logger
import com.shz.logger.LoggerType
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.database.entity.LogEntity
import com.shz.logger.kit.export.LoggerCollectionPrinter
import com.shz.logger.kit.export.file.LoggerFileWriter
import com.shz.logger.kit.utils.handle

object LoggerShareUtility {

    private val printer = LoggerCollectionPrinter()

    fun shareAsFile(context: Context, logs: List<LogEntity>) {
        val printedLogs = printer.createLogEntriesOutput(logs)
        val file = LoggerFileWriter.writeToFile(context, printedLogs)
        val uri = FileProvider.getUriForFile(context, context.applicationContext.packageName + ".provider", file)
        LoggerKit.Debugger.print("SHARE", "URI: $uri")
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            putExtra(Intent.EXTRA_STREAM, uri)
            type = "*/*"
            flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        }
        LoggerKit.Debugger.print("SHARE", "Sending share intent: $shareIntent")
        handle { context.startActivity(shareIntent) }
    }
}