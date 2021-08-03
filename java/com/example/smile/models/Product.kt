package com.example.smile.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    var imgByt:String?="",
    var name:String="",
    var type:String="",
    var overview:String="",
    var comanyName:String="",
    var price:Double=0.0,
    var perfumStart:String="",
    var PerfumEnd:String="",
    var PerfumEast:String="",
    var nature:String="",
):Parcelable {
}