package com.example.smile.data

import com.example.smile.data.db.entity.ProductEntity
import com.example.smile.util.BoundResource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class UserRepo @Inject constructor(var firebaseSource: FirebaseSource,var databaseSource:DataBaseSource) {

}