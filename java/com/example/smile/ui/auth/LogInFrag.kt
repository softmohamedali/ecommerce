package com.example.smile.ui.auth

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.smile.R
import com.example.smile.util.StatusResult
import com.example.smile.viewmodels.UserViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import kotlinx.android.synthetic.main.fragment_sing_up.view.*

@AndroidEntryPoint
class LogInFrag : Fragment() {
    private  val userViewModel: UserViewModel by viewModels<UserViewModel>()
    lateinit var viewLay:View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflat
        // e the layout for this fragment
        viewLay= inflater.inflate(R.layout.fragment_log_in, container, false)
        viewLay.login_btn_register.setOnClickListener {
            findNavController().navigate(R.id.action_logInFrag_to_singUpFrag)
        }

        viewLay.login_btn_login.setOnClickListener {
            val email=viewLay.login_et_email.text.toString()
            val password=viewLay.login_et_password.text.toString()
            if (email.isEmpty())
            {
                viewLay.login_et_email.error="email empty"
                viewLay.login_et_email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                viewLay.login_et_email.error="email not vaild"
                viewLay.login_et_email.requestFocus()
                return@setOnClickListener
            }
            if (password.length<1)
            {
                viewLay.login_et_password.error="password is empty"
                viewLay.login_et_password.requestFocus()
                return@setOnClickListener
            }
            if (password.length<6)
            {
                viewLay.login_et_password.error="password length less than 6"
                viewLay.login_et_password.requestFocus()
                return@setOnClickListener
            }

            userViewModel.singInWithEmail(email, password)
        }
        userViewModel.isSingIn.observe(viewLifecycleOwner, {
            cheakUserStatus(it)
        })

        return viewLay
    }

    private fun cheakUserStatus(it: StatusResult<FirebaseUser>?) {
        when {
            it is StatusResult.Loading -> {
                viewLay.login_pr.visibility = View.VISIBLE
            }
            it is StatusResult.Error -> {
                Toast.makeText(requireActivity(), it.massage, Toast.LENGTH_SHORT).show()
                viewLay.login_pr.visibility = View.INVISIBLE
            }
            it is StatusResult.OnSuccsess -> {
                val action= LogInFragDirections.actionLogInFragToBodyActivity2(userViewModel.user?.uid.toString())
                findNavController().navigate(action)
                viewLay.login_pr.visibility = View.INVISIBLE
            }
        }
    }

}