package com.nexters.zzallang.harusal2.application

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nexters.zzallang.harusal2.data.dao.BudgetDao
import com.nexters.zzallang.harusal2.data.dao.StatementDao
import com.nexters.zzallang.harusal2.data.entity.Budget
import com.nexters.zzallang.harusal2.data.entity.Statement

@Database(entities = [Statement::class, Budget::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun statementDao(): StatementDao
    abstract fun periodDao(): BudgetDao

    companion object {
        val instance: AppDatabase by lazy {
            buildDatabase()
        }

        private fun buildDatabase(): AppDatabase =
            Room.databaseBuilder(App.app, AppDatabase::class.java, "sample.db").build()
    }
}