package com.shz.logger

import com.shz.logger.middleware.LoggerMiddleware
import kotlin.reflect.KClass

object Logger {

    private val middlewares: ArrayList<LoggerMiddleware> = arrayListOf()
    private var printer: LoggerPrinter = AdvancedLoggerPrinter()

    /**
     * Member of **[Logger]**.
     * Allows to configure current Logger.
     */
    object Configuration {

        fun setPrinter(printer: LoggerPrinter) {
            Logger.printer = printer
        }

        fun addMiddleware(vararg middlewares: LoggerMiddleware) {
            Logger.middlewares.addAll(middlewares)
        }

        fun clearAllMiddlewares() {
            Logger.middlewares.clear()
        }
    }

    fun v(clazz: Class<*>, message: Any) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, null)
    }

    fun v(clazz: Class<*>, prefix: String, message: Any) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, null)
    }

    fun v(clazz: Class<*>, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), null, t)
    }

    fun v(clazz: Class<*>, prefix: String, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.VERBOSE, clazz.TAG, message.toString(), prefix, t)
    }
    //---
    fun v(clazz: KClass<*>, message: Any) {
        v(clazz.java, message)
    }

    fun v(clazz: KClass<*>, prefix: String, message: Any) {
        v(clazz.java, prefix, message)
    }

    fun v(clazz: KClass<*>, message: Any, t: Throwable?) {
        v(clazz.java, message, t)
    }

    fun v(clazz: KClass<*>, prefix: String, message: Any, t: Throwable?) {
        v(clazz.java, prefix, message, t)
    }
    //--
    fun d(clazz: Class<*>, message: Any) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), null, null)
    }

    fun d(clazz: Class<*>, prefix: String, message: Any) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, null)
    }

    fun d(clazz: Class<*>, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
    }

    fun d(clazz: Class<*>, prefix: String, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.DEBUG, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.DEBUG, clazz.TAG, message.toString(), null, t)
    }
    //--
    fun d(clazz: KClass<*>, message: Any) {
        d(clazz.java, message)
    }

    fun d(clazz: KClass<*>, prefix: String, message: Any) {
        d(clazz.java, prefix, message)
    }

    fun d(clazz: KClass<*>, message: Any, t: Throwable?) {
        d(clazz.java, message, t)
    }

    fun d(clazz: KClass<*>, prefix: String, message: Any, t: Throwable?) {
        d(clazz.java, prefix, message, t)
    }
    //--
    fun i(clazz: Class<*>, message: Any) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), null, null)
    }

    fun i(clazz: Class<*>, prefix: String, message: Any) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), prefix, null)
    }

    fun i(clazz: Class<*>, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), null, t)
    }

    fun i(clazz: Class<*>, prefix: String, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.INFO, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.INFO, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.INFO, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    fun i(clazz: KClass<*>, message: Any) {
        i(clazz.java, message)
    }

    fun i(clazz: KClass<*>, prefix: String, message: Any) {
        i(clazz.java, prefix, message)
    }

    fun i(clazz: KClass<*>, message: Any, t: Throwable?) {
        i(clazz.java, message, t)
    }

    fun i(clazz: KClass<*>, prefix: String, message: Any, t: Throwable?) {
        i(clazz.java, prefix, message, t)
    }
    //--
    fun w(clazz: Class<*>, message: Any) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), null, null)
    }

    fun w(clazz: Class<*>, prefix: String, message: Any) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, null)
    }

    fun w(clazz: Class<*>, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), null, t)
    }

    fun w(clazz: Class<*>, prefix: String, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.WARNING, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    fun w(clazz: KClass<*>, message: Any) {
        w(clazz.java, message)
    }

    fun w(clazz: KClass<*>, prefix: String, message: Any) {
        w(clazz.java, prefix, message)
    }

    fun w(clazz: KClass<*>, message: Any, t: Throwable?) {
        w(clazz.java, message, t)
    }

    fun w(clazz: KClass<*>, prefix: String, message: Any, t: Throwable?) {
        w(clazz.java, prefix, message, t)
    }
    //--
    fun e(clazz: Class<*>, message: Any) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, null)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, null)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, null)
    }

    fun e(clazz: Class<*>, prefix: String, message: Any) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, null)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, null)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, null)
    }

    fun e(clazz: Class<*>, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, t)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, t)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), null, t)
    }

    fun e(clazz: Class<*>, prefix: String, message: Any, t: Throwable?) {
        onLogReceived(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, t)
        printer.log(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(LoggerType.EXCEPTION, clazz.TAG, message.toString(), prefix, t)
    }
    //--
    fun e(clazz: KClass<*>, message: Any) {
        e(clazz.java, message)
    }

    fun e(clazz: KClass<*>, prefix: String, message: Any) {
        e(clazz.java, prefix, message)
    }

    fun e(clazz: KClass<*>, message: Any, t: Throwable?) {
        e(clazz.java, message, t)
    }

    fun e(clazz: KClass<*>, prefix: String, message: Any, t: Throwable?) {
        e(clazz.java, prefix, message, t)
    }

    fun log(type: LoggerType, clazz: Class<*>, prefix: String?, message: Any, t: Throwable) {
        onLogReceived(type, clazz.TAG, message.toString(), prefix, t)
        printer.log(type, clazz.TAG, message.toString(), prefix, t)
        onLogPrinted(type, clazz.TAG, message.toString(), prefix, t)
    }

    fun log(type: LoggerType, clazz: KClass<*>, prefix: String?, message: Any, t: Throwable) {
        log(type, clazz.java, prefix, message.toString(), t)
    }

    private fun onLogReceived(type: LoggerType, tag: String, message: Any, prefix: String?, t: Throwable?) {
        middlewares.forEach { it.onLogReceived(type, tag, message.toString(), prefix, t) }
    }

    private fun onLogPrinted(type: LoggerType, tag: String, message: Any, prefix: String?, t: Throwable?) {
        middlewares.forEach { it.onLogPrinted(type, tag, message.toString(), prefix, t) }
    }
}