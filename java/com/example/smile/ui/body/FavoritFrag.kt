package com.example.smile.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.smile.R
import com.example.smile.adapters.FavItemCardAdapter
import com.example.smile.viewmodels.BodyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorit.view.*

@AndroidEntryPoint
class FavoritFrag : Fragment() {

    private val bodyViewModel: BodyViewModel by viewModels<BodyViewModel>()

    private lateinit var mview:View
    private val mAdapter by lazy { FavItemCardAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview =inflater.inflate(R.layout.fragment_favorit, container, false)
        setUprecy()
        bodyViewModel.favProduct.observe(viewLifecycleOwner,{
            mAdapter.setData(it)
        })

        return mview
    }

    fun setUprecy()
    {
        mview.recy_favfrag.apply {
            adapter=mAdapter
            layoutManager=LinearLayoutManager(requireActivity(),RecyclerView.VERTICAL,false)
        }
    }

}