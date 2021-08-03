package com.example.smile.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.smile.data.UserRepo
import com.example.smile.util.StatusResult
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(var userRepo:UserRepo, applicaton:Application):AndroidViewModel(applicaton) {

    var isRegester=MutableLiveData<StatusResult<FirebaseUser>>()
    var isSingIn=MutableLiveData<StatusResult<FirebaseUser>>()
    var user=userRepo.firebaseSource.currentUser()

    fun singInWithEmail(email: String,password: String) {
        isSingIn.value=StatusResult.Loading()
        if (hasInternetConnection())
        {
            userRepo.firebaseSource.singInWithEmail(email, password).addOnSuccessListener {

                isSingIn.value=StatusResult.OnSuccsess(it.user)


            }.addOnFailureListener {
                isSingIn.value=StatusResult.Error(it.message)
            }
        }
        else
        {
            isSingIn.value=StatusResult.Error("No internet connection")
        }

    }

    fun createEmailWithEmail(name:String, email:String, password:String) {
        if (hasInternetConnection())
        {
            isRegester.value=StatusResult.Loading()
            userRepo.firebaseSource.createUserWithEmail(name, email, password).addOnCompleteListener {
                if (it.isSuccessful)
                {
                    userRepo.firebaseSource.saveUserName(name)
                    isRegester.value=StatusResult.OnSuccsess()
                }
                else{
                    isRegester.value=StatusResult.Error(it.exception?.message)
                }
            }
        }
        else{
            isRegester.value=StatusResult.Error("No internet connection")
        }

    }


    fun logOut()
    {
        userRepo.firebaseSource.logOut()
        user=null
    }


    private fun hasInternetConnection():Boolean {
        val connectivityManger=getApplication<Application>()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?:return false
        val networkCapability=connectivityManger.getNetworkCapabilities(netWorkActive) ?:return false
        when{
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else->return false
        }

    }

}