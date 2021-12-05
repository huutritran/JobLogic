package com.example.joblogic.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.joblogic.data.datasources.model.ProductModel

@Database(entities = [ProductModel::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDAO(): ProductDAO

    companion object {
        const val DATABASE_NAME = "job_logic_db.db"
    }
}