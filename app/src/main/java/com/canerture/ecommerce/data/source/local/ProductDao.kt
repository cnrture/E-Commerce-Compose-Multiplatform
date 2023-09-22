package com.canerture.ecommerce.data.source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.canerture.ecommerce.data.model.response.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM products")
    suspend fun getProducts(): List<ProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: ProductEntity)

    @Delete
    suspend fun deleteFromFavorites(product: ProductEntity)

    @Query("SELECT title FROM products")
    suspend fun getFavoriteTitles(): List<String>

    @Query("DELETE FROM products")
    suspend fun clearFavorites()
}