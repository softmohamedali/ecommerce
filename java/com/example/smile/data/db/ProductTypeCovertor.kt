package com.example.smile.data.db

import androidx.room.TypeConverter
import com.example.smile.models.Payment
import com.example.smile.models.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductTypeCovertor {
    val json=Gson()


    @TypeConverter
    fun ProductTOString(products:List<Product>):String
    {
        return json.toJson(products)
    }

    @TypeConverter
    fun StringTOProduct(string:String):List<Product>
    {
        return json.fromJson(string,object :TypeToken<List<Product>>(){}.type)
    }

    @TypeConverter
    fun oneProductTOString(products:Product):String
    {
        return json.toJson(products)
    }

    @TypeConverter
    fun StringTOOneProduct(string:String):Product
    {
        return json.fromJson(string,object :TypeToken<Product>(){}.type)
    }

    @TypeConverter
    fun onePaymentTOString(payment:Payment):String
    {
        return json.toJson(payment)
    }

    @TypeConverter
    fun StringTOOnePayment(string:String):Payment
    {
        return json.fromJson(string,object :TypeToken<Payment>(){}.type)
    }


}