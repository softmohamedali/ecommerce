package com.example.smile.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.smile.R
import com.example.smile.data.db.entity.OneProductEntity
import com.example.smile.models.Product
import com.example.smile.viewmodels.BodyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_info.view.*

@AndroidEntryPoint
class InfoFragment : Fragment() {
    var isSave:Boolean=false

    var saveid:Int=0

    val bodyViewModel: BodyViewModel by viewModels<BodyViewModel>()

    private val navargs by navArgs<InfoFragmentArgs>()

    lateinit var mview:View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mview=inflater.inflate(R.layout.fragment_info, container, false)

        val product=navargs.pro

        bodyViewModel.favProduct.observe(requireActivity(),{list->
            list.forEach {
                isSave = product.imgByt==it.product.imgByt
                saveid=it.id
            }
            if (isSave)
            {
                mview.img_addfav_infoactivity.setColorFilter(ContextCompat.getColor(requireContext(),R.color.yellow))
            }else{
                mview.img_addfav_infoactivity.setColorFilter(ContextCompat.getColor(requireContext(),R.color.text1))
            }
        })



        mview.type_info_activ.text=product.type
        mview.tv_title_info_activ.text=product.name
        mview.brand_info_activ.text=product.comanyName
        mview.gprice_info_activ.text=product.price.toString()
        mview.overview_info_activ.text=product.overview
        mview.tv_base_info_activity.text=product.PerfumEnd
        mview.tv_front_info_activity.text=product.perfumStart
        mview.tv_east_info_activity.text=product.PerfumEast
        mview.tv_nature_info_activ.text=product.nature
        mview.tv_20price_info_activity.text=(((product.price)*8).toString())+ "  E.G"
        mview.tv_30price_info_activity.text=(((product.price)*12).toString())+ "  E.G"
        mview.tv_40price_info_activity.text=(((product.price)*16).toString())+ "  E.G"
        mview.tv_50price_info_activity.text=(((product.price)*20).toString())+ "  E.G"
        mview.img_info_activ.load(product.imgByt){
            error(R.drawable.ic_error)
        }



        mview.img_addfav_infoactivity.setOnClickListener {
            if (isSave)
            {
                deleteFAv(product)
            }
            else{
                saveFav(product)
            }
        }

        mview.back_btn_infofrag.setOnClickListener {
            findNavController().navigate(R.id.action_infoFragment_to_mainFrag)
        }
        mview.fab_info_activ.setOnClickListener {
            val action=InfoFragmentDirections.actionInfoFragmentToAddCartFragment(product)
            findNavController().navigate(action)
        }

        return mview
    }

    fun deleteFAv(product: Product){
        mview.img_addfav_infoactivity.setColorFilter(ContextCompat.getColor(requireContext(),R.color.lightgray))
        isSave=false
        val favProduct= OneProductEntity(saveid,product)
        bodyViewModel.deleteFav(favProduct)
    }

    fun saveFav(product: Product){
        isSave=true
        val favProduct= OneProductEntity(0,product)
        bodyViewModel.insertFavProductTodb(favProduct)
        mview.img_addfav_infoactivity.setColorFilter(ContextCompat.getColor(requireContext(),R.color.yellow))
    }


}
