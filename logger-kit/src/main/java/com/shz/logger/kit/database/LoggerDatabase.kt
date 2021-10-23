package com.shz.logger.kit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shz.logger.kit.LoggerKit
import com.shz.logger.kit.database.dao.LogDao
import com.shz.logger.kit.database.entity.LogEntity

@Database(
    entities = [
        LogEntity::class,
    ],
    version = LoggerKit.Config.DB_VERSION,
    exportSchema = false
)
abstract class LoggerDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
}

object LoggerDatabaseProvider {
    private lateinit var database: LoggerDatabase

    fun init(context: Context) {
        database = Room
            .databaseBuilder(context, LoggerDatabase::class.java, LoggerKit.Config.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provide(): LoggerDatabase = database
}