package com.example.smile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smile.data.db.entity.PaymentEntity
import com.example.smile.databinding.ItemCardLayoutBinding
import com.example.smile.models.Payment
import com.example.smile.util.MyDiffUtil
import com.example.smile.viewmodels.BodyViewModel

class PaymentItemCardAdapter(var context: Fragment,var viewMode:BodyViewModel):RecyclerView.Adapter<PaymentItemCardAdapter.VH>() {


    private var payments= emptyList<PaymentEntity>()
    class VH(var myview:ItemCardLayoutBinding):RecyclerView.ViewHolder(myview.root){

        fun bind(payment: Payment){
            myview.payment=payment
            myview.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):VH{
                val viewInfl=ItemCardLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return VH(viewInfl)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.from(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(payments[position].payment)
        var paymentSelect=payments[position].payment
        holder.myview.apply {
            var value=tvValueItemcart.text.toString().toInt()
            tvValueItemcart.text = value.toString()
            tvTotalPCardfrag.text=(value*(payments[position].payment.price)).toString()
            btnPlusItemcart.setOnClickListener {
                tvValueItemcart.text=(++value).toString()
                paymentSelect.count=value
                paymentSelect.totalPrice=tvTotalPCardfrag.text.toString().toDouble()
                viewMode.upDataPayments(PaymentEntity(payments[position].id,paymentSelect))
            }
            btnMinItemcart.setOnClickListener {
                if (value>1) {
                    tvValueItemcart.text=(--value).toString()
                    paymentSelect.count=value
                    paymentSelect.totalPrice=tvTotalPCardfrag.text.toString().toDouble()
                    viewMode.upDataPayments(PaymentEntity(payments[position].id,paymentSelect))

                }
            }
            btnDeleteCardfrag.setOnClickListener {
                viewMode.deletPayments(PaymentEntity(payments[position].id,paymentSelect))
                deleteItem(position)
            }
            tvValueItemcart.doOnTextChanged { text, start, before, count ->
                tvTotalPCardfrag.text=(value*(payments[position].payment.price)).toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    fun setData(myPayment: List<PaymentEntity>)
    {
        val myDiffUtil=MyDiffUtil(payments,myPayment)
        val result=DiffUtil.calculateDiff(myDiffUtil)
        payments=myPayment
        result.dispatchUpdatesTo(this)
    }
    fun deleteItem(position: Int){
        if (payments.size==1){
            setData(emptyList())
        }
        payments.drop(position)
        notifyDataSetChanged()
    }
}












