package com.example.smile.util

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun Application.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_LONG).show()
}

fun <T> LiveData<T>.observerOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>)
{
    observe(lifecycleOwner,object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer.onChanged(t)
        }
    })
}