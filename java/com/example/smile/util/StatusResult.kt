package com.example.smile.util

sealed class StatusResult<T> (
    var data:T?=null,
    var massage:String?=null
        ){
    class OnSuccsess<T>(data: T?=null): StatusResult<T>(data)
    class Error<T>(massage: String?,data: T?=null): StatusResult<T>(data,massage)
    class Loading<T>(data: T?=null): StatusResult<T>(data)
}