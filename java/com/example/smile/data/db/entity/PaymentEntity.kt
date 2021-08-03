package com.example.smile.data.db.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.smile.models.Payment
import com.example.smile.util.Constant
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = Constant.PAYMENTS_TABLE_NAME)
class PaymentEntity(
        @PrimaryKey(autoGenerate = true)
        var id:Int=0,
        var payment: Payment
):Parcelable {

}