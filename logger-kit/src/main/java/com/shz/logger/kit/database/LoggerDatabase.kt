package com.shz.logger.kit.database

import androidx.room.Database
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