package com.example.smile.util

import android.app.DownloadManager
import kotlinx.coroutines.flow.*

inline fun <resType,reqType>BoundResource(
    crossinline query:() -> Flow<resType>,
    crossinline fetsh:suspend () -> reqType,
    crossinline saveFetshrequest:(reqType) -> Unit,
    crossinline shouldFetsh:(resType) -> Boolean = {true}
) = flow{
    val data=query().first()
    val flow=if (shouldFetsh(data)){
        emit(StatusResult.Loading(data))

        try {
            saveFetshrequest(fetsh())
            query().map { StatusResult.OnSuccsess(it) }
        } catch (e:Throwable){
            query().map { StatusResult.Error(e.message,it) }
        }
    }else{
        query().map { StatusResult.OnSuccsess(it) }
    }
    emitAll(flow)
}