package com.example.smile.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Singleton

@Module
@InstallIn(ActivityRetainedComponent::class)
object FirebaseModule {

    @ActivityRetainedScoped
    @Provides
    fun provideFirebaseFireStorage():FirebaseStorage
    {
        return FirebaseStorage.getInstance()
    }

    @ActivityRetainedScoped
    @Provides
    fun provideFirebaseAuth():FirebaseAuth
    {
        return FirebaseAuth.getInstance()
    }

    @ActivityRetainedScoped
    @Provides
    fun provideFirebasefirestore():FirebaseFirestore
    {
        return FirebaseFirestore.getInstance()
    }
}