package com.example.smile.data

import androidx.room.withTransaction
import com.example.smile.data.db.entity.OneProductEntity
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.data.db.entity.ProductEntity
import com.example.smile.data.db.ProductDAO
import com.example.smile.data.db.ProductDataBase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class DataBaseSource @Inject constructor(var productDAo:ProductDAO,var productDataBase: ProductDataBase) {

    suspend fun insertNewAndDelete(product:ProductEntity)
    {
        productDataBase.withTransaction {
            productDAo.deleteAllProducts()
            productDAo.insertProducts(product)
        }
    }

    suspend fun insertProduct(product:ProductEntity){
        productDAo.insertProducts(product)
    }

    fun getAllProduct():Flow<List<ProductEntity>>{
        return productDAo.getAllProduct()
    }

    suspend fun insertFavProduct(product:OneProductEntity){
        productDAo.insertFavProduct(product)
    }

    fun getAllFavProduct():Flow<List<OneProductEntity>>{
        return productDAo.getAllFavProduct()
    }

    suspend fun deleteFav(favProduct:OneProductEntity){
        productDAo.deleteFav(favProduct)
    }

    suspend fun insertPayment(paymentEntity: PaymentEntity)
    {
        productDAo.insertPayment(paymentEntity)
    }

    suspend fun deletePayment(paymentEntity: PaymentEntity)
    {
        productDAo.deletePayment(paymentEntity)
    }

    suspend fun deleteAllPayment()
    {
        productDAo.deleteAllPayment()
    }

    fun getAllPayments():Flow<List<PaymentEntity>>
    {
        return productDAo.getAllPayments()
    }

    suspend fun upDataPayments(paymentEntity: PaymentEntity)
    {
        return productDAo.upDatePayment(paymentEntity)
    }
}


























