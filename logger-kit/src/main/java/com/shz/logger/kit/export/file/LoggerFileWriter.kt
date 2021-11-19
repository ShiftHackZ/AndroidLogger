package com.shz.logger.kit.export.file

import android.content.Context
import com.shz.logger.Logger
import com.shz.logger.LoggerType
import com.shz.logger.kit.LoggerKit
import java.io.File

internal object LoggerFileWriter {

    fun writeToFile(context: Context, input: String): File {
        val filename = "logs_${context.applicationContext.packageName}_${System.currentTimeMillis()}.txt"
        val path = context.filesDir
        val outputDir = File(path, "logs")
        outputDir.mkdirs()
        val outputFile = File(outputDir, filename)
        outputFile.appendText(input, Charsets.UTF_8)
        LoggerKit.Debugger.print("SHARE", "Logs written to file: ${outputFile.path}")
        return outputFile
    }
}