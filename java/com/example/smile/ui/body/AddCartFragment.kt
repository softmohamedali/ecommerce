package com.example.smile.ui.body

import android.os.Bundle
import android.util.Log
import android.view.Gravity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController

import androidx.navigation.fragment.navArgs
import com.example.smile.R
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.models.Payment
import com.example.smile.util.Constant
import com.example.smile.viewmodels.BodyViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_add_cart.view.*

@AndroidEntryPoint
class AddCartFragment : BottomSheetDialogFragment(){

    private val bodyViewModel: BodyViewModel by viewModels<BodyViewModel>()
    private val navArgs by navArgs<AddCartFragmentArgs>()
    lateinit var mview:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_add_cart, container, false)

        val myProduct=navArgs.prouct
        Log.d("iiid"," to addd")
        mview.btn_addtocart_addcart.setOnClickListener {
            Log.d("iiid","clicked to addd")
            val idrg=mview.rg_size_addcart.checkedRadioButtonId
            val radiocheak=mview.findViewById<RadioButton>(idrg)
            Log.d("iiid",radiocheak.id.toString())
            when(radiocheak.id){
                R.id.rb_size20_addcart ->{
                    bodyViewModel.insertPayment(PaymentEntity(0,
                            Payment( myProduct,
                                    Constant.SIZE_20,
                                    ((myProduct.price)*8),
                                    1,
                                    ((myProduct.price)*8)
                            )))
                }
                R.id.rb_size30_addcacrt ->{
                    bodyViewModel.insertPayment(PaymentEntity(0,
                            Payment(myProduct,
                                    Constant.SIZE_30,
                                    ((myProduct.price)*12),
                                    1,
                                    ((myProduct.price)*12)
                            )))
                }
                R.id.rb_size40_addcart ->{
                    bodyViewModel.insertPayment(PaymentEntity(0,
                            Payment(myProduct,
                                    Constant.SIZE_40,
                                    ((myProduct.price)*16),
                                    1,
                                    ((myProduct.price)*16)
                            )))
                }
                R.id.rb_size50_addcart ->{
                    bodyViewModel.insertPayment(PaymentEntity(0,
                            Payment(myProduct,
                                    Constant.SIZE_50,
                                    ((myProduct.price)*22),
                                    1,
                                    ((myProduct.price)*22)
                            )))
                }

            }
            val toastView=LayoutInflater.from(requireContext()).inflate(R.layout.toast,null,false)
            val myToast=Toast(requireContext())
            myToast.view=toastView
            myToast.duration=Toast.LENGTH_SHORT
            myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            myToast.show()
            findNavController().popBackStack()
        }


        return mview
    }



}


