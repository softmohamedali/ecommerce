package com.example.smile.ui.addproduct

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.smile.R
import com.example.smile.models.Product
import com.example.smile.util.StatusResult
import com.example.smile.viewmodels.BodyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_product_actvity.*
import java.io.ByteArrayOutputStream

@AndroidEntryPoint
class AddProductActvity : AppCompatActivity() {

    private val bodyViewModel:BodyViewModel by viewModels<BodyViewModel>()

    var mimgbyte:ByteArray?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product_actvity)
        supportActionBar?.setHomeButtonEnabled(true)

        bodyViewModel.isUpload.observe(this,{
            when{
                it is StatusResult.Error ->{
                    Toast.makeText(applicationContext,it.massage,Toast.LENGTH_SHORT).show()
                    add_pro.visibility=View.INVISIBLE
                }
                it is StatusResult.Loading -> {
                    add_pro.visibility=View.VISIBLE
                }
                it is StatusResult.OnSuccsess ->{
                    Toast.makeText(applicationContext,"success Uploaad",Toast.LENGTH_SHORT).show()
                    add_pro.visibility=View.INVISIBLE
                    finish()
                }
            }
        })

        add_product_img.setOnClickListener {
            val myIntent=Intent().apply {
                action=Intent.ACTION_GET_CONTENT
                type="image/*"
                putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/png","image/jpeg"))
            }
            startActivityForResult(Intent.createChooser(myIntent,"chosse img"),2)
        }

        add_btn_upload.setOnClickListener {
            val imgbyte=mimgbyte
            val name=add_et_perfname
            val type=add_et_typeperf
            val overView=add_et_overview
            val price=add_et_gramprice
            val companyName=add_et_companyname
            val perfumStart=add_et_perfumstart
            val perfumEast=add_et_perfumeast
            val perfumEnd=add_et_perfumend
            val nature=add_et_nature
            if (name.text.isEmpty())
            {
                name.error="name is require"
                name.requestFocus()
                return@setOnClickListener
            }
            if (type.text.isEmpty())
            {
                type.error="type is require"
                type.requestFocus()
                return@setOnClickListener
            }
            if (overView.text.isEmpty())
            {
                overView.error="overView is require"
                overView.requestFocus()
                return@setOnClickListener
            }
            if (price.text.isEmpty())
            {
                price.error="price is require"
                price.requestFocus()
                return@setOnClickListener
            }
            if (companyName.text.isEmpty())
            {
                companyName.error="companyName is require"
                companyName.requestFocus()
                return@setOnClickListener
            }
            if (perfumStart.text.isEmpty())
            {
                perfumStart.error="perfum Start is require"
                perfumStart.requestFocus()
                return@setOnClickListener
            }
            if (perfumEast.text.isEmpty())
            {
                perfumEast.error="perfum East is require"
                perfumEast.requestFocus()
                return@setOnClickListener
            }
            if (perfumEnd.text.isEmpty())
            {
                perfumEnd.error="perfum End is require"
                perfumEnd.requestFocus()
                return@setOnClickListener
            }
            if (nature.text.isEmpty())
            {
                nature.error="nature is require"
                nature.requestFocus()
                return@setOnClickListener
            }
            if (imgbyte==null)
            {
                Toast.makeText(this,"please chosse img to upload",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            bodyViewModel.uploadProduct(Product(
               null,
                name.text.toString(),
                type.text.toString(),
                overView.text.toString(),
                companyName.text.toString(),
                price.text.toString().toDouble(),
                perfumStart.text.toString(),
                perfumEnd.text.toString(),
                perfumEast.text.toString(),
                nature.text.toString()
            ),imgbyte)
        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==android.R.id.home)
        {
            finish()
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==2 && resultCode==Activity.RESULT_OK&& data!=null&&data.data!=null)
        {
            val imgUriSelected=data.data
            add_product_img.setImageURI(imgUriSelected)
            val imgBitbapSelected=MediaStore.Images.Media.getBitmap(this.contentResolver,imgUriSelected)
            val byteArrayOutStrem=ByteArrayOutputStream()
            imgBitbapSelected.compress(Bitmap.CompressFormat.JPEG,25,byteArrayOutStrem)
            val byteArray=byteArrayOutStrem.toByteArray()
            mimgbyte=byteArray
        }
    }

}