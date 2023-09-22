package com.canerture.ecommerce.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.canerture.ecommerce.data.model.response.ProductEntity

@Database(entities = [ProductEntity::class], version = 1, exportSchema = false)
abstract class ProductRoomDB : RoomDatabase() {

    abstract fun productsDao(): ProductDao
}