package com.example.smile.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smile.models.Product
import com.example.smile.util.Constant
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constant.PRODUCT_TABLE_NAME)
class ProductEntity (
    var products:List<Product>
):Parcelable {
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}