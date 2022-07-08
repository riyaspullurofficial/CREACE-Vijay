package com.riyas.creacevijay.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert
    suspend fun insertDataDao(product: Product)

    @Query("SELECT * FROM product_table ")
    fun getAllProduct():LiveData<List<Product>>
}