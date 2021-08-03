package com.example.smile.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Payment(
        var product:Product,
        var size:String,
        var price:Double,
        var count:Int,
        var totalPrice:Double
):Parcelable {
}