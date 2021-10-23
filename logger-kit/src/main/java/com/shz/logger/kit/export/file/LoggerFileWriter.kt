package com.shz.logger.kit.export.file

import android.content.Context
import java.io.File

object LoggerFileWriter {

    fun writeToFile(context: Context, input: String): File {
        val filename = "logs_${context.applicationContext.packageName}_${System.currentTimeMillis()}.txt"
        val path = context.filesDir
        val outputDir = File(path, "logs")
        outputDir.mkdirs()
        val outputFile = File(outputDir, filename)
        outputFile.appendText(input, Charsets.UTF_8)
        return outputFile
    }
}