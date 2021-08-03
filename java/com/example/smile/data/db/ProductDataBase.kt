package com.example.smile.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.smile.data.db.entity.OneProductEntity
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.data.db.entity.ProductEntity

@Database(
    entities = arrayOf(ProductEntity::class,OneProductEntity::class,PaymentEntity::class),
    version = 2,
    exportSchema = false
)
@TypeConverters(ProductTypeCovertor::class)
abstract class ProductDataBase:RoomDatabase(){
    abstract fun getDao():ProductDAO
}