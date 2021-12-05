package com.example.joblogic.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.joblogic.data.datasources.model.ProductModel

@Dao
interface ProductDAO {

    @Query("SELECT * FROM ItemToSell")
    suspend fun getSellList(): List<ProductModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(value: List<ProductModel>)
}