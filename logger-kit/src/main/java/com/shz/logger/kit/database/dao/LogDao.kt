package com.shz.logger.kit.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.shz.logger.kit.database.contract.LogEntityContract
import com.shz.logger.kit.database.entity.LogEntity

@Dao
internal interface LogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item: LogEntity)

    @RawQuery
    fun query(request: SupportSQLiteQuery): List<LogEntity>

    @Query("DELETE FROM ${LogEntityContract.TABLE_NAME}")
    fun clear()

    @Query("SELECT COUNT(${LogEntityContract.ID}) FROM ${LogEntityContract.TABLE_NAME}")
    fun observeCount(): LiveData<Int>
}