package com.shz.logger.kit.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shz.logger.kit.database.contract.LogEntityContract

@Entity(tableName = LogEntityContract.TABLE_NAME)
data class LogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = LogEntityContract.ID)
    val id: Long,
    @ColumnInfo(name = LogEntityContract.SESSION_ID)
    val sessionId: String,
    @ColumnInfo(name = LogEntityContract.TIMESTAMP)
    val timestamp: Long,
    @ColumnInfo(name = LogEntityContract.TYPE)
    val type: String,
    @ColumnInfo(name = LogEntityContract.CLASS)
    val className: String,
    @ColumnInfo(name = LogEntityContract.PREFIX)
    val prefix: String,
    @ColumnInfo(name = LogEntityContract.MESSAGE)
    val message: String,
    @ColumnInfo(name = LogEntityContract.STACKTRACE)
    val stacktrace: String
)