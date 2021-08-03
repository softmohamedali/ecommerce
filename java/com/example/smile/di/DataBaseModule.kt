package com.example.smile.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.smile.data.db.ProductDataBase
import com.example.smile.util.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context:Context)=
            Room.databaseBuilder(context,
            ProductDataBase::class.java,
            Constant.PRODUCT_DATABASE_NAME)
                    .build()


    @Provides
    @Singleton
    fun ProvideDAo(dataBase: ProductDataBase)=dataBase.getDao()
}