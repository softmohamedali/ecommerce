package com.example.smile.bindingadapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.example.smile.R
import com.example.smile.models.Product
import com.example.smile.ui.body.FavoritFragDirections
import com.example.smile.ui.body.MainFragDirections

class ProductRowBindingAdapter {
    companion object{

        @BindingAdapter("setimg",requireAll = true)
        @JvmStatic
        fun setImg(view:ImageView,url:String)
        {
            view.load(url){
                this.crossfade(600)
                error(R.drawable.ic_add_photo)
            }
        }

        @BindingAdapter("onclickRecyItem",requireAll = true)
        @JvmStatic
        fun onclickRecyItem(view:View,pro:Product)
        {
            view.setOnClickListener {
                val action=MainFragDirections.actionMainFragToInfoFragment(pro)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("onclickFavRecyItem",requireAll = true)
        @JvmStatic
        fun onclickFavRecyItem(view:View,pro:Product)
        {
            view.setOnClickListener {
                val action=FavoritFragDirections.actionFavoritFragToInfoFragment(pro)
                view.findNavController().navigate(action)
            }
        }

        @BindingAdapter("setPrice",requireAll = true)
        @JvmStatic
        fun setPrice(view:TextView,price:Double)
        {
            view.text=price.toString()
        }

        @BindingAdapter("setInt",requireAll = true)
        @JvmStatic
        fun setPrice(view:TextView,price:Int)
        {
            view.text=price.toString()
        }
    }
}