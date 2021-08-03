package com.example.smile.data.db

import androidx.room.*
import com.example.smile.data.db.entity.OneProductEntity
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.data.db.entity.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product:ProductEntity)

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Query("SELECT * FROM products")
    fun getAllProduct():Flow<List<ProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavProduct(product: OneProductEntity)

    @Query("SELECT * FROM oneproduct")
    fun getAllFavProduct():Flow<List<OneProductEntity>>

    @Delete
    fun deleteFav(product:OneProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(paymentEntity: PaymentEntity)

    @Query("SELECT * FROM payments")
    fun getAllPayments():Flow<List<PaymentEntity>>

    @Delete
    suspend fun deletePayment(paymentEntity: PaymentEntity)

    @Query("DELETE FROM payments")
    suspend fun deleteAllPayment()

    @Update
    suspend fun upDatePayment(paymentEntity: PaymentEntity)

}