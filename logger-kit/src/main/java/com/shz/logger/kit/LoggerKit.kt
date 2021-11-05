package com.shz.logger.kit

import android.app.Application
import android.content.Intent
import androidx.room.Room
import com.shz.logger.Logger
import com.shz.logger.LoggerType
import com.shz.logger.kit.database.LoggerDatabase
import com.shz.logger.kit.database.LoggerDatabaseProvider
import com.shz.logger.kit.middleware.DatabaseLoggerMiddleware
import com.shz.logger.kit.presentation.viewer.LogViewerActivity
import com.shz.logger.kit.utils.handle
import com.shz.logger.middleware.LoggerMiddleware
import java.util.*

/**
 * LoggerKit extends functionality of Logger library.
 * Main LoggerKit SDK Entry point.
 *
 * Features:
 * - local log collection storage middleware;
 * - built in ui-toolkit for log viewing, filtering, exporting.
 *
 * @author Dmitriy Moroz (ShiftHackZ)
 * Website: https://moroz.cc
 * E-Mail: dmitriy@moroz.cc
 */
object LoggerKit {

    /**
     * LoggerKit library version
     */
    const val VERSION = "1.0"

    /**
     * Pointer to current [Application] instance.
     */
    private lateinit var application: Application

    /**
     * Instance of database collector middleware.
     * Collects all logs and saves to local sqlite database.
     */
    private lateinit var databaseMiddleware: DatabaseLoggerMiddleware

    /**
     * Initializes [LoggerKit] and [DatabaseLoggerMiddleware] for log collecting.
     *
     * @param app instance of your [Application] member declared in AndroidManifest.
     */
    @JvmStatic
    fun initialize(app: Application): LoggerKit {
        generateSessionId()
        LoggerDatabaseProvider.init(app)
        application = app
        databaseMiddleware = DatabaseLoggerMiddleware(
            LoggerDatabaseProvider.provide().logDao(),
        )
        addMiddleware(databaseMiddleware)
        return this
    }

    /**
     * Allows to define if [LoggerKit] should print it's own debug information to logs.
     * Note, [LoggerKit] debug messages does not processed by any of [LoggerMiddleware].
     */
    @JvmStatic
    fun setDebugMode(debug: Boolean): LoggerKit {
        Debugger.printDebug = debug
        Debugger.print("Mode", "Debug mode state changed to: $debug")
        return this
    }

    /**
     * Generates new session-id parameter, which indicates as runtime identifier of log entry.
     * Called automatically during [LoggerKit] initialization, only needs to be called if necessary.
     */
    @JvmStatic
    fun generateSessionId(): LoggerKit {
        Config.sessionId = UUID.randomUUID().toString()
        Debugger.print("Session", "Generated session-id: ${Config.sessionId}")
        return this
    }

    /**
     * Opens screen with formatted list of previously collected logs.
     * Allows user to view, search, filter and export logs in runtime.
     */
    @JvmStatic
    fun openLogViewer() = handle {
        Debugger.print("UI", "Requested to open LoggerKit viewer")
        application.startActivity(
            Intent(application, LogViewerActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            })
    }

    /**
     * Member of [LoggerKit], [Logger].
     * Duplicates call of corresponding [Logger] method.
     * Allows to add [LoggerMiddleware] instance.
     *
     * @see Logger
     * @see LoggerMiddleware
     * @param middleware instance of corresponding [LoggerMiddleware].
     */
    @JvmStatic
    fun addMiddleware(middleware: LoggerMiddleware): LoggerKit {
        Logger.addMiddleware(middleware)
        Debugger.print("MIDDLEWARE", "Adding new middleware: $middleware")
        return this
    }

    /**
     * Member of [LoggerKit], [Logger].
     * Duplicates call of corresponding [Logger] method.
     * Allows to add one or multiple [LoggerMiddleware] instances.
     *
     * @see Logger
     * @see LoggerMiddleware
     * @param middlewares collection of corresponding [LoggerMiddleware] objects.
     */
    @JvmStatic
    fun addMiddlewares(vararg middlewares: LoggerMiddleware): LoggerKit {
        Debugger.print("MIDDLEWARE", "Adding ${middlewares.size} new middlewares")
        middlewares.forEach(LoggerKit::addMiddleware)
        return this
    }

    /**
     * Member of [LoggerKit], [Logger].
     * Duplicates call of corresponding [Logger] method.
     * Allows to stop using some instance of [LoggerMiddleware] while log processing.
     *
     * @see Logger
     * @see LoggerMiddleware
     * @param middleware instance that needs to be released from middlewares processor.
     */
    @JvmStatic
    fun removeMiddleware(middleware: LoggerMiddleware): LoggerKit {
        Debugger.print("MIDDLEWARE", "Removing middleware $middleware")
        Logger.removeMiddleware(middleware)
        return this
    }

    /**
     * Member of [LoggerKit], [Logger].
     * Duplicates call of corresponding [Logger] method.
     * Allows to stop using all previous added instances of [LoggerMiddleware] for log processing.
     *
     * @see Logger
     * @see LoggerMiddleware
     */
    @JvmStatic
    fun clearAllMiddlewares(): LoggerKit {
        Debugger.print("MIDDLEWARE", "Removing all previous middlewares")
        Logger.clearAllMiddlewares()
        return this
    }

    /**
     * Contains current LoggerKit configuration params.
     */
    object Config {
        /**
         * Defines [LoggerDatabase] file name.
         */
        const val DB_NAME = "logger_kit"

        /**
         * Defines [LoggerDatabase] schema version.
         * Should be increased in case of schema modification.
         */
        const val DB_VERSION = 1

        /**
         * Runtime parameter. Contains unique [LoggerKit] session-id, which saved in database.
         * Allows to perform log entries filtering by single application runtime session.
         */
        var sessionId: String = ""

    }

    object Debugger {

        /**
         * Flags that defines if [LoggerKit] should print it's own debug information to logs.
         * Note, [LoggerKit] debug messages does not processed by any of [LoggerMiddleware].
         */
        var printDebug: Boolean = false

        fun toggle() {
            printDebug = !printDebug
        }

        fun print(prefix: String, message: String, e: Throwable? = null) {
            if (printDebug) Logger.print(LoggerType.INFO, LoggerKit::class, prefix, message, e)
        }
    }
}