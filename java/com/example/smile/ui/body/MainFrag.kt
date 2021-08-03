package com.example.smile.ui.body

import am.networkconnectivity.NetworkConnectivity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.smile.R
import com.example.smile.adapters.ProductAdapter
import com.example.smile.databinding.FragmentMainBinding
import com.example.smile.ui.addproduct.AddProductActvity
import com.example.smile.ui.auth.MainActivity
import com.example.smile.util.InternetConnection
import com.example.smile.util.StatusResult
import com.example.smile.viewmodels.BodyViewModel
import com.example.smile.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFrag : Fragment() {
    private var _binding:FragmentMainBinding?=null
    private val binding  get() =_binding!!

    private  val userViewModel: UserViewModel by viewModels<UserViewModel>()
    private  val bodyViewModel:BodyViewModel by viewModels<BodyViewModel>()
    private lateinit var internnetConnecion:InternetConnection
    val mAdapter:ProductAdapter by lazy { ProductAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentMainBinding.inflate(layoutInflater)
        binding.lifecycleOwner=viewLifecycleOwner
        setUpRecycle()
        binding.btnMnueMainfrag.setOnClickListener(object:View.OnClickListener{
            override fun onClick(v: View?) {
                binding.btnMnueMainfrag.performLongClick()
            }

        } )
        registerForContextMenu(binding.btnMnueMainfrag)



        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val interntConnectivity= NetworkConnectivity(requireActivity()).observe(requireActivity(),
                { networkStatus ->
                    when (networkStatus!!) {
                        NetworkConnectivity.NetworkStatus.OnConnected -> {
                            Toast.makeText(requireContext(), "Connection is back", Toast.LENGTH_SHORT).show()
                            Log.d("myac", "has internert")
                            requestDatafromFirebase()
                        }
                        NetworkConnectivity.NetworkStatus.OnWaiting -> {
                            Toast.makeText(requireContext(), "Connection is waiting", Toast.LENGTH_SHORT).show()
                        }
                        NetworkConnectivity.NetworkStatus.OnLost -> {
                            Toast.makeText(requireContext(), "Connection is lost", Toast.LENGTH_SHORT).show()
                            Log.d("myac", "no internert")
                            requestDataFromDatabase()
                        }
                    }
                })
    }
    fun requestDataFromDatabase()
    {
        Log.d("mylog","db")

        bodyViewModel.product.observe(binding.lifecycleOwner!!,{
            if(it.isNotEmpty())
            {
                mAdapter.setData(it[0].products)
                showShimmer(false)
                showShimmer(false)
            }
            else{
                requestDatafromFirebase()
            }

        })
    }

    fun requestDatafromFirebase(){
        Log.d("mylog","firebse")
        bodyViewModel.getAllProducts()
        bodyViewModel.products.observe(binding.lifecycleOwner!!,{
            when{
                it is StatusResult.Loading ->{
                    showError(false)
                    showShimmer(true)
                }
                it is StatusResult.OnSuccsess ->{
                    showError(false)
                    showShimmer(false)
                    mAdapter.setData(it.data!!)

                }
                it is StatusResult.Error ->{
                    binding.tvMainFrag.text=it.massage
                    binding.tvMainFrag
                    showError(true)
                    showShimmer(false)
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        bodyViewModel.getAllProducts()
    }

    fun setUpRecycle()
    {
        binding.recyMainFrag.adapter=mAdapter
        val layoutManager=GridLayoutManager(requireContext(),2)
        binding.recyMainFrag.layoutManager=layoutManager

    }

    fun showError(show:Boolean)
    {
        if (show)
        {
            binding.imgMainFrag.visibility=View.VISIBLE
            binding.tvMainFrag.visibility=View.VISIBLE
        }
        else{
            binding.imgMainFrag.visibility=View.INVISIBLE
            binding.tvMainFrag.visibility=View.INVISIBLE
        }

    }

    fun showShimmer(show:Boolean)
    {
        if (show)
        {
            binding.recyMainFrag.showShimmer()
        }
        else{
            binding.recyMainFrag.hideShimmer()
        }
    }

    private fun hasInternetConnection():Boolean {
        val connectivityManger=context
                ?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netWorkActive = connectivityManger.activeNetwork ?:return false
        val networkCapability=connectivityManger.getNetworkCapabilities(netWorkActive) ?:return false
        when{
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            networkCapability.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
            else->return false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.body_main_menu,menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val meanId=item.itemId
        when(meanId){
            R.id.contact_with_us ->{
                val contactIntent= Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + "soft.mohamedali@gmail.com"))
                requireContext().startActivity(contactIntent)
                return true
            }
            R.id.add_product ->{
               if (bodyViewModel.currentUser?.email=="smile@gmail.com")
               {
                   val addProductIntent= Intent(requireActivity(),AddProductActvity::class.java)
                   startActivity(addProductIntent)
                   return true
               }else{
                   val toastView=LayoutInflater.from(requireContext()).inflate(R.layout.toast2,null,false)
                   val myToast=Toast(requireContext())
                   myToast.view=toastView
                   myToast.duration=Toast.LENGTH_SHORT
                   myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                   myToast.show()
                   Log.d("toast","this department only for admin")
                   return true
               }

            }
            R.id.about_us ->{

                return true
            }
            R.id.log_out ->{
                bodyViewModel.logOut()
                startActivity(Intent(requireActivity(),MainActivity::class.java))
                requireActivity().finish()
                return true
            }
            else ->{
                return false
            }
        }
    }
}