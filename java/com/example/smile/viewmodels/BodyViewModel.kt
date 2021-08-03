package com.example.smile.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.example.smile.data.UserRepo
import com.example.smile.data.db.entity.OneProductEntity
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.data.db.entity.ProductEntity
import com.example.smile.models.Product
import com.example.smile.util.BoundResource
import com.example.smile.util.StatusResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BodyViewModel @Inject constructor(
    application: Application,
    var repo:UserRepo
):AndroidViewModel(application) {


    //Room Database Section

    val product=repo.databaseSource.getAllProduct().asLiveData()

    val favProduct=repo.databaseSource.getAllFavProduct().asLiveData()

    val payments=repo.databaseSource.getAllPayments().asLiveData()

    fun insertProductTodb(products:ProductEntity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.databaseSource.insertProduct(products)
        }
    }

    fun insertFavProductTodb(product:OneProductEntity)
    {
        viewModelScope.launch(Dispatchers.IO) {
            repo.databaseSource.insertFavProduct(product)
        }
    }

    fun insertPayment(paymentEntity: PaymentEntity)
    {
        viewModelScope.launch {
            repo.databaseSource.insertPayment(paymentEntity)
        }
    }

    fun deleteFav(favProduct: OneProductEntity)
    {
        viewModelScope.launch (Dispatchers.IO){
            repo.databaseSource.deleteFav(favProduct)
        }
    }

    fun deletPayments(paymentEntity: PaymentEntity)
    {
        viewModelScope.launch {
            repo.databaseSource.deletePayment(paymentEntity)
        }
    }

    fun deletAllPayments()
    {
        viewModelScope.launch {
            repo.databaseSource.deleteAllPayment()
        }
    }

    fun upDataPayments(paymentEntity: PaymentEntity)
    {
        viewModelScope.launch {
            repo.databaseSource.upDataPayments(paymentEntity)
        }
    }




    //FireBase Section

    val isUpload:MutableLiveData<StatusResult<String>> = MutableLiveData()
    val products:MutableLiveData<StatusResult<List<Product>>> = MutableLiveData()
    val currentUser=repo.firebaseSource.currentUser()


    fun getAllProducts()
    {
        products.value=StatusResult.Loading()
        if (hasInternetConnection())
        {
            val produ=repo.firebaseSource.getAllProducts(){
                if (it.isNullOrEmpty())
                {
                    products.value=StatusResult.Error("No products")
                }
                else
                {
                    products.value=StatusResult.OnSuccsess(it)
                    val productEntity=ProductEntity(it)
                    insertProductTodb(productEntity)
                }
            }


        }
        else{
            products.value=StatusResult.Error("No Internet Connection")
        }

    }

    fun uploadProduct(product: Product,imgByteArray: ByteArray)
    {
        isUpload.value=StatusResult.Loading()
        if (hasInternetConnection())
        {
            uploadImg(imgByteArray){

                //get img path url to save it in products info
                val path=it
                val pro=product
                pro.imgByt=path
                repo.firebaseSource.upLoadProduct(product){
                    it.addOnCompleteListener {
                        if (it.isSuccessful){
                            isUpload.value=StatusResult.OnSuccsess()
                        }
                        else{
                            isUpload.value=StatusResult.Error(it.exception?.message.toString())
                        }
                    }
                }
            }
        }
        else{
            isUpload.value=StatusResult.Error("No Internet Connection")
        }
    }

    private fun uploadImg(imgByteArray: ByteArray,onsucsess:(path:String)-> Unit)=
        repo.firebaseSource.upLoadImg(imgByteArray,onsucsess)

    fun logOut(){
        repo.firebaseSource.logOut()
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