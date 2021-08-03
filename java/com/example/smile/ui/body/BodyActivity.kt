package com.example.smile.ui.body

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.smile.R
import com.example.smile.ui.addproduct.AddProductActvity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_body.*

@AndroidEntryPoint
class BodyActivity : AppCompatActivity(),NavController.OnDestinationChangedListener {
    lateinit var myBodyNav:NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_body)


        myBodyNav=findNavController(R.id.fragment2)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.mainFrag,R.id.favoritFrag))
        bnv_body_activity.setupWithNavController(myBodyNav)
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragment2) as NavHostFragment
        val navController=navHostFragment.navController
        navController.addOnDestinationChangedListener(this)





    }



    fun searchAndBottomViewVisibility(visibility:Boolean) {
        if (visibility)
        {
            bnv_body_activity.visibility=View.VISIBLE
        }
        else{
            bnv_body_activity.visibility=View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.body_main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.add_product)
        {
            startActivity(Intent(this, AddProductActvity::class.java))
        }
        return true
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp()||myBodyNav.navigateUp()
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when(destination.id)
        {
            R.id.infoFragment ->{
                searchAndBottomViewVisibility(false)
            }
            R.id.addCartFragment ->{
                searchAndBottomViewVisibility(false)
            }
            else ->{
                searchAndBottomViewVisibility(true)
            }
        }
    }
}