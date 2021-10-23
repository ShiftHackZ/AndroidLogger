package com.shz.logger.kit.database.dao

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.shz.logger.kit.database.entity.LogEntity

@Dao
interface LogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LogEntity)

    @RawQuery
    fun query(request: SupportSQLiteQuery): List<LogEntity>
}