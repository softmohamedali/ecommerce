package com.example.smile.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smile.models.Product
import com.example.smile.util.Constant
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constant.ONEPRODUCT_TABLE_NAME)
class OneProductEntity (
        @PrimaryKey(autoGenerate = true)
        var id:Int,
        var product: Product
        ):Parcelable{
}