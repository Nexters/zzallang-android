package com.nexters.zzallang.harusal2.data.dao

import androidx.room.*
import com.nexters.zzallang.harusal2.data.entity.Statement

@Dao
interface StatementDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStatement(statement: Statement)

    @Query("select * from statement where id = :id")
    suspend fun getStatement(id: Long): Statement

    @Update
    suspend fun updateStatement(value: Statement)
}