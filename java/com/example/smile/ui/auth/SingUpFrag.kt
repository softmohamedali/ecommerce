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
import com.example.smile.viewmodels.UserViewModel
import com.example.smile.R
import com.example.smile.util.StatusResult
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_log_in.view.*
import kotlinx.android.synthetic.main.fragment_sing_up.view.*

@AndroidEntryPoint
class SingUpFrag : Fragment() {
    private  val userViewModel: UserViewModel by viewModels<UserViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_sing_up, container, false)
        view.singup_btn_register.setOnClickListener {
            val name=view.singup_et_username.text.toString()
            val email=view.singup_et_email.text.toString()
            val password=view.singup_et_password.text.toString()
            if (name.isEmpty())
            {
                view.singup_et_username.error="name required"
                view.singup_et_username.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty())
            {
                view.singup_et_email.error="email empty"
                view.singup_et_email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                view.singup_et_email.error="email not vaild"
                view.singup_et_email.requestFocus()
                return@setOnClickListener
            }
            if (password.length<1)
            {
                view.singup_et_password.error="password is empty"
                view.singup_et_password.requestFocus()
                return@setOnClickListener
            }
            if (password.length<6)
            {
                view.singup_et_password.error="password length less than 6"
                view.singup_et_password.requestFocus()
                return@setOnClickListener
            }
            userViewModel.createEmailWithEmail(name,email,password)
        }
        userViewModel.isRegester.observe(viewLifecycleOwner, {
            when {
                it is StatusResult.Loading -> {
                    view.singup_pro.visibility = View.VISIBLE
                }
                it is StatusResult.Error -> {
                    Toast.makeText(requireActivity(), it.massage, Toast.LENGTH_SHORT).show()
                    view.singup_pro.visibility = View.INVISIBLE
                }
                it is StatusResult.OnSuccsess -> {
                    findNavController().navigate(R.id.action_singUpFrag_to_logInFrag)
                    view.singup_pro.visibility = View.INVISIBLE
                }
            }
        })

        return view
    }


}