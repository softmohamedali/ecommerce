package com.example.smile.ui.body

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.smile.R
import kotlinx.android.synthetic.main.fragment_client_payments.view.*

class ClientPaymentsInfoFragment : Fragment() {
    private lateinit var mview:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mview= inflater.inflate(R.layout.fragment_client_payments, container, false)

        val Items = listOf("Home", "work")
        val Adapter = ArrayAdapter(requireContext(), R.layout.dropmenu_layout, Items)
        mview.et_placetype_clientpayinfo.setAdapter(Adapter)
//        val CityItems = listOf("Cairo","Menofia","Tanta", "Allepo", "Dubai","Riad")
//        val CityAdapter = ArrayAdapter(requireContext(), R.layout.dropmenu_layout, CityItems)
//        mview.etad_city_clientpayinfo.setAdapter(CityAdapter)

        mview.checkBox_map_clientpayinfo.setOnClickListener {
            findNavController().navigate(R.id.action_clientPaymentsFragment_to_myMapsFragment)
        }
        return mview
    }

}