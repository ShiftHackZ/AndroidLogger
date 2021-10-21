package com.shz.logger.middleware

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Log
import com.shz.logger.Logger
import com.shz.logger.LoggerType
import com.shz.logger.TAG
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class LoggerNetworkMiddleware(
    private val context: Context,
    private val requestUrl: String = DEFAULT_URL
) : LoggerMiddleware {

    private val executorService = Executors.newFixedThreadPool(EXECUTOR_POOL_SIZE)

    override fun onLogReceived(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) = Unit

    override fun onLogPrinted(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ) {
        if (tag == this::class.TAG) return
        executorService.execute {
            formatRequest(loggerType, tag, content, prefix, throwable).let(::sendRequest)
        }
    }

    private fun formatRequest(
        loggerType: LoggerType,
        tag: String,
        content: String,
        prefix: String?,
        throwable: Throwable?
    ): String {
        val json = JSONObject().apply {
            put("package", MetaData().packageName)
            put("app_version", MetaData().versionName)
            put("app_code", MetaData().versionCode)
            put("device_id", MetaData().androidId)
            put("device_name", MetaData().deviceModel)
            put("device_os", "${MetaData().androidVersion} (${MetaData().androidSdk})")
            put("type", "$loggerType")
            put("class", tag)
            put("message", content)
            put("prefix", prefix ?: "")
            throwable?.let { t ->
                put("exception", jsonEscape(Log.getStackTraceString(t)))
            }
        }
        return json.toString()
    }

    private fun sendRequest(body: String) = try {
        val url = URL(requestUrl)
        val httpConnection = (url.openConnection() as HttpURLConnection).apply {
            connectTimeout = CONNECTION_TIMEOUT
            requestMethod = CONNECTION_REST_METHOD
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "*/*")
            doOutput = true
            doInput = true
        }
        val output = httpConnection.outputStream
        output.write(body.toByteArray(charset(DEFAULT_CHARSET)))
        output.close()
        val input: InputStream = BufferedInputStream(httpConnection.inputStream)
        input.close()
        httpConnection.disconnect()

    } catch (e: Exception) {
        Logger.e(this::class, "sendRequest", e.message, e)
    }

    private fun jsonEscape(body: String): String = body.apply {
        replace("\\", "\\\\")
        replace("\"", "\\\"")
        replace("\b", "\\b")
        replace("\n", "\\n")
        replace("\r", "\\r")
        replace("\t", "\\t")
    }

    inner class MetaData {
        val packageName: String
            get() = context.applicationContext.packageName

        val versionName: String
            get() = context.packageManager.getPackageInfo(packageName, 0).versionName

        val versionCode: String
            get() = context.packageManager.getPackageInfo(packageName, 0).versionCode.toString()

        val androidId: String
            @SuppressLint("HardwareIds")
            get() = try {
                Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
            } catch (e: Exception) {
                ""
            }

        val androidVersion: String
            get() = Build.VERSION.RELEASE

        val androidSdk: Int
            get() = Build.VERSION.SDK_INT

        val deviceModel: String
            get() = Build.MODEL
    }

    companion object {
        private const val EXECUTOR_POOL_SIZE = 4
        private const val CONNECTION_TIMEOUT = 10_000
        private const val CONNECTION_REST_METHOD = "POST"
        private const val DEFAULT_CHARSET = "UTF-8"
        private const val DEFAULT_URL = "https://api.moroz.cc/log/api-v2.php/records/android"
    }
}