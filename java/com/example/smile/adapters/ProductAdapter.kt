package com.example.smile.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smile.databinding.ProductRowLayoutBinding
import com.example.smile.models.Product
import com.example.smile.util.MyDiffUtil

class ProductAdapter:RecyclerView.Adapter<ProductAdapter.MyViewHolder>() {

    var products = emptyList<Product>()
    class MyViewHolder(var bind:ProductRowLayoutBinding):RecyclerView.ViewHolder(bind.root) {

        fun binding(product:Product)
        {
            bind.product=product
            bind.executePendingBindings()
        }

        companion object
        {
            fun from(view:ViewGroup):MyViewHolder
            {
                val layoutBinding=ProductRowLayoutBinding.inflate(LayoutInflater.from(view.context),view,false)
                return MyViewHolder(layoutBinding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding(products[position])
    }

    override fun getItemCount(): Int {
        return products.size
    }

    fun setData(newProduct:List<Product>)
    {
        val diffutil=MyDiffUtil(products,newProduct)
        val calc=DiffUtil.calculateDiff(diffutil)
        products=newProduct
        calc.dispatchUpdatesTo(this)

    }
}