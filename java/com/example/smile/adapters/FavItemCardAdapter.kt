package com.example.smile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smile.data.db.entity.OneProductEntity
import com.example.smile.databinding.MyfavItemCardBinding
import com.example.smile.util.MyDiffUtil

class FavItemCardAdapter (): RecyclerView.Adapter<FavItemCardAdapter.VH>() {

    private var products= emptyList<OneProductEntity>()

    class VH(var myview: MyfavItemCardBinding): RecyclerView.ViewHolder(myview.root){

        fun bind(product: OneProductEntity){
            myview.product=product
            myview.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):VH{
                val viewInfo= MyfavItemCardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
                return VH(viewInfo)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH.from(parent)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(myPayment: List<OneProductEntity>)
    {
        val myDiffUtil= MyDiffUtil(products,myPayment)
        val result= DiffUtil.calculateDiff(myDiffUtil)
        products=myPayment
        result.dispatchUpdatesTo(this)
    }
}
