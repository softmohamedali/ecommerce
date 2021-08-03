package com.example.smile.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smile.R
import com.example.smile.adapters.PaymentItemCardAdapter
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.viewmodels.BodyViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_payments.*
import kotlinx.android.synthetic.main.fragment_payments.view.*

@AndroidEntryPoint
class CartFragment : Fragment() {

    private val bodyViewModel: BodyViewModel by viewModels<BodyViewModel>()
    private val mAdapter by lazy { PaymentItemCardAdapter(this,bodyViewModel) }
    lateinit var mview:View
    private var totalPrice=0.0
    private var paymentsCount=0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview=inflater.inflate(R.layout.fragment_payments, container, false)
        setUpRecy()
        bodyViewModel.payments.observe(viewLifecycleOwner,{
            if(!it.isNullOrEmpty())
            {
                paymentsCount=it.size
                mAdapter.setData(it)
                showNoPaymentHint(false)
                calcTotalPayments(it)
            }else{
                showNoPaymentHint(true)
            }

        })
        mview.btn_completepay_paymentfrag.setOnClickListener {
            if (paymentsCount<=0){
                Snackbar.make(mview.payments_frag,"there is no payments",Snackbar.LENGTH_SHORT)
                        .setAction("ok",{}).show()
            }
            else{
                findNavController().navigate(R.id.action_cartFrag_to_clientPaymentsFragment)
            }
        }
        return mview
    }

    private fun calcTotalPayments(it: List<PaymentEntity>) {
        totalPrice=0.0
        it.forEach {
            totalPrice+=it.payment.totalPrice
        }
        tv_totalpr_paymentsfrag.text=totalPrice.toString()+" $"
    }

    fun setUpRecy()
    {
        mview.recy_cardfrag.apply {
            adapter=mAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

    fun showNoPaymentHint(show:Boolean)
    {
        if (show)
        {
            mview.tv_nopayment_paymentsfrag.visibility=View.VISIBLE
            mview.img_img_paymentfrag.visibility=View.VISIBLE
        }else{
            mview.tv_nopayment_paymentsfrag.visibility=View.INVISIBLE
            mview.img_img_paymentfrag.visibility=View.INVISIBLE
        }
    }


}