package com.example.smile.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.example.smile.R
import com.example.smile.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private  val userViewModel: UserViewModel by viewModels<UserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (userViewModel.user != null) {
            Log.d("my log", "inactiity")
            findNavController(R.id.fragment).navigate(R.id.action_logInFrag_to_bodyActivity2)
            finish()
        }
    }
}