package com.shz.logger

import com.shz.logger.middleware.LoggerMiddleware
import com.shz.logger.printer.AdvancedLoggerPrinter
import com.shz.logger.printer.LoggerPrinter
import kotlin.reflect.KClass

/**
 * Main Logger SDK Entry point.
 * Contains method of configuration and logging.
 *
 * @author Dmitriy Moroz (ShiftHackZ)
 * Website: https://moroz.cc
 * E-Mail: dmitriy@moroz.cc
 */
object Logger {

    /**
     * Collection of [LoggerMiddleware] that is used for log pre/post processing.
     */
    private val middlewares: ArrayList<LoggerMiddleware> = arrayListOf()

    /**
     * Current instance of [LoggerPrinter] that is used for log output.
     */
    private var printer: LoggerPrinter = AdvancedLoggerPrinter()

    /**
     * Member of [Logger].
     * Allows to set custom [LoggerPrinter] interface that prints log output.
     *
     * @see LoggerPrinter
     * @param printer instance of [LoggerPrinter] to use for output.
     */
    @JvmStatic
    fun setPrinter(printer: LoggerPrinter): Logger {
        Logger.printer = printer
        return this
    }

    /**
     * Member of [Logger].
     * Allows to add [LoggerMiddleware] instances.
     *
     * @see LoggerMiddleware
     * @param middleware instance of corresponding [LoggerMiddleware].
     */
    @JvmStatic
    fun addMiddleware(middleware: LoggerMiddleware): Logger {
        middlewares.add(middleware)
        return this
    }

    /**
     * Member of [Logger].
     * Allows to add one or multiple [LoggerMiddleware] instances.
     *
     * @see LoggerMiddleware
     * @param middlewares collection of corresponding [LoggerMiddleware] objects.
     */
    @JvmStatic
    fun addMiddlewares(vararg middlewares: LoggerMiddleware): Logger {
        Logger.middlewares.addAll(middlewares)
        return this
    }

    /**
     * Member of [Logger].
     * Allows to stop using some instance of [LoggerMiddleware] while log processing.
     *
     * @see LoggerMiddleware
     * @param middleware instance that needs to be released from middlewares processor.
     */
    @JvmStatic
    fun removeMiddleware(middleware: LoggerMiddleware): Logger {
        if (!middleware.isWhitelisted) middlewares.remove(middleware)
        return this
    }

    /**
     * Member of [Logger].
     * Allows to stop using all previous added instances of [LoggerMiddleware] for log processing.
     *
     * @see LoggerMiddleware
     */
    @JvmStatic
    fun clearAllMiddlewares(): Logger {
        val nonKitMiddleware = middlewares.filter { it.isWhitelisted }
        middlewares.removeAll(nonKitMiddleware)
        return this
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun v(clazz: Class<*>, message: Any?) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun v(clazz: Class<*>, prefix: String, message: Any?) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun v(clazz: Class<*>, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun v(clazz: Class<*>, prefix: String, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun v(clazz: KClass<*>, message: Any?) {
        v(clazz.java, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun v(clazz: KClass<*>, prefix: String, message: Any?) {
        v(clazz.java, prefix, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun v(clazz: KClass<*>, message: Any?, t: Throwable?) {
        v(clazz.java, message, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.VERBOSE] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun v(clazz: KClass<*>, prefix: String, message: Any?, t: Throwable?) {
        v(clazz.java, prefix, message, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun d(clazz: Class<*>, message: Any?) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), null, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun d(clazz: Class<*>, prefix: String, message: Any?) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun d(clazz: Class<*>, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun d(clazz: Class<*>, prefix: String, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun d(clazz: KClass<*>, message: Any?) {
        d(clazz.java, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun d(clazz: KClass<*>, prefix: String, message: Any?) {
        d(clazz.java, prefix, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun d(clazz: KClass<*>, message: Any?, t: Throwable?) {
        d(clazz.java, message, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.DEBUG] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun d(clazz: KClass<*>, prefix: String, message: Any?, t: Throwable?) {
        d(clazz.java, prefix, message, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun i(clazz: Class<*>, message: Any?) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), null, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun i(clazz: Class<*>, prefix: String, message: Any?) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), prefix, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun i(clazz: Class<*>, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), null, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun i(clazz: Class<*>, prefix: String, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun i(clazz: KClass<*>, message: Any?) {
        i(clazz.java, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun i(clazz: KClass<*>, prefix: String, message: Any?) {
        i(clazz.java, prefix, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun i(clazz: KClass<*>, message: Any?, t: Throwable?) {
        i(clazz.java, message, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.INFO] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun i(clazz: KClass<*>, prefix: String, message: Any?, t: Throwable?) {
        i(clazz.java, prefix, message, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun w(clazz: Class<*>, message: Any?) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), null, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun w(clazz: Class<*>, prefix: String, message: Any?) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun w(clazz: Class<*>, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), null, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun w(clazz: Class<*>, prefix: String, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun w(clazz: KClass<*>, message: Any?) {
        w(clazz.java, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun w(clazz: KClass<*>, prefix: String, message: Any?) {
        w(clazz.java, prefix, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun w(clazz: KClass<*>, message: Any?, t: Throwable?) {
        w(clazz.java, message, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.WARNING] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun w(clazz: KClass<*>, prefix: String, message: Any?, t: Throwable?) {
        w(clazz.java, prefix, message, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun e(clazz: Class<*>, message: Any?) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun e(clazz: Class<*>, prefix: String, message: Any?) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, null)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller java class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun e(clazz: Class<*>, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun e(clazz: Class<*>, prefix: String, message: Any?, t: Throwable?) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print.
     */
    @JvmStatic
    fun e(clazz: KClass<*>, message: Any?) {
        e(clazz.java, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print.
     */
    @JvmStatic
    fun e(clazz: KClass<*>, prefix: String, message: Any?) {
        e(clazz.java, prefix, message)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun e(clazz: KClass<*>, message: Any?, t: Throwable?) {
        e(clazz.java, message, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds [LoggerType.EXCEPTION] log message.
     *
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun e(clazz: KClass<*>, prefix: String, message: Any?, t: Throwable?) {
        e(clazz.java, prefix, message, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds log message.
     *
     * @see LoggerType
     *
     * @param type type of log message;
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun log(type: LoggerType, clazz: Class<*>, prefix: String?, message: Any?, t: Throwable) {
        onLogReceived(type, clazz.TAG, message.toString(), prefix, t)
        printer.log(type, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(type, clazz.TAG, message.toString(), prefix, t)
    }

    /**
     * Member of [Logger].
     * Prints and proceeds log message.
     *
     * @see LoggerType
     *
     * @param type type of log message;
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun log(type: LoggerType, clazz: KClass<*>, prefix: String?, message: Any?, t: Throwable) {
        log(type, clazz.java, prefix, message.toString(), t)
    }

    /**
     * Member of [Logger].
     * Prints log message and does not use any middleware and log processing.
     *
     * @see LoggerType
     *
     * @param type type of log message;
     * @param clazz caller java class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun print(type: LoggerType, clazz: Class<*>, prefix: String?, message: Any?, t: Throwable? = null) {
        printer.log(type, clazz.TAG, message.toString(), prefix, t)
    }

    /**
     * Member of [Logger].
     * Prints log message and does not use any middleware and log processing.
     *
     * @see LoggerType
     *
     * @param type type of log message;
     * @param clazz caller kotlin class instance;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    @JvmStatic
    fun print(type: LoggerType, clazz: KClass<*>, prefix: String?, message: Any?, t: Throwable? = null) {
        print(type, clazz.java, prefix, message, t)
    }

    /**
     * Performs log messages pre-processing for all logger middlewares.
     *
     * @see LoggerMiddleware
     *
     * @param type type of log message;
     * @param tag represents caller class name;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    private fun onLogReceived(type: LoggerType, tag: String, message: Any?, prefix: String?, t: Throwable?) {
        middlewares.forEach { it.onLogReceived(type, tag, message.toString(), prefix, t) }
    }

    /**
     * Performs log messages post-processing for all logger middlewares.
     *
     * @see LoggerMiddleware
     *
     * @param type type of log message;
     * @param tag represents caller class name;
     * @param prefix tag that can be used for filtering or grouping logs;
     * @param message message to print;
     * @param t exception, represented as instance of [Throwable].
     */
    private fun onLogPrinted(type: LoggerType, tag: String, message: Any?, prefix: String?, t: Throwable?) {
        middlewares.forEach { it.onLogPrinted(type, tag, message.toString(), prefix, t) }
    }
}