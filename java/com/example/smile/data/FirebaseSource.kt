package com.example.smile.data

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.smile.models.Product
import com.example.smile.models.User
import com.example.smile.util.Constant
import com.example.smile.util.StatusResult
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class FirebaseSource @Inject constructor (
    private var firebaseAuth:FirebaseAuth,
    private var firebaseFirestore: FirebaseFirestore,
    private var firebaseStorage: FirebaseStorage

){



     fun upLoadProduct(product:Product,result:(task:Task<Void>)->Unit) {
         val pro=product
         val firebaseRes=firebaseFirestore.collection(Constant.PRODDUCT_COLLECTION_NAME)
                 .document().set(pro)
         result(firebaseRes)
    }

    fun upLoadImg(imgByteArray: ByteArray,onsucsess:(path:String)-> Unit)
    {
        val ref=firebaseStorage.reference
                .child("${firebaseAuth.currentUser?.uid}/images/${UUID.nameUUIDFromBytes(imgByteArray)}")
        ref.putBytes(imgByteArray).continueWithTask {
            if (!it.isSuccessful) {
                it.exception?.let {
                    throw it
                }
            }
            ref.downloadUrl
        }.addOnCompleteListener {
            if (it.isSuccessful) {
                onsucsess(it.result.toString())
            }
        }
    }


    fun getAllProducts(onlisten:(ArrayList<Product> )-> Unit)
    {
        val products= ArrayList<Product>()
        firebaseFirestore.collection("products")
            .addSnapshotListener { value, error ->
                if (error!=null)
                {
                    return@addSnapshotListener
                }
                else{
                    value?.documents?.forEach {
                        val pro=it.toObject(Product::class.java)!!
                        products.add(pro)
                    }
                }
                onlisten(products)
            }
    }



    // Authentacation
    fun createUserWithEmail(name:String,email:String,password:String)=
        firebaseAuth.createUserWithEmailAndPassword(email,password)

    fun singInWithEmail(email:String,password: String)=
        firebaseAuth.signInWithEmailAndPassword(email, password)

    fun saveUserName(name: String)=firebaseFirestore.collection(Constant.USER_COLLECTION_NAME)
        .document(firebaseAuth.currentUser?.uid.toString()).set(User(name))

    fun logOut()=firebaseAuth.signOut()

    fun currentUser()=firebaseAuth.currentUser

}