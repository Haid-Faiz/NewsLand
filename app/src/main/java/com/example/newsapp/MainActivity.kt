package com.example.newsapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.newsapp.Adapter.MyAdapter
import com.example.newsapp.Adapter.NewsItemClicked
import com.example.newsapp.Fragments.BBCFragment
import com.example.newsapp.Fragments.HomeFragment
import com.example.newsapp.Fragments.ScienceFragment
import com.example.newsapp.Fragments.TechFragment
import com.example.newsapp.Mdodel.NewsData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var homeFragment = HomeFragment()

//        supportFragmentManager.beginTransaction().replace(R.id.container, homeFragment).commit()


        // This code is of google material libarary
//        bottom_nav.setOnNavigationItemSelectedListener { item ->
//            lateinit var currentFrag: Fragment
//
//            when (item.itemId) {
//                R.id.menu_headline -> currentFrag = HomeFragment()
//                R.id.menu_science -> currentFrag = ScienceFragment()
//                R.id.menu_tech -> currentFrag = TechFragment()
//                R.id.menu_bbc -> currentFrag = BBCFragment()
//            }
//
//            supportFragmentManager.beginTransaction().replace(R.id.container, currentFrag).commit()
//
//            true
//        }
//        bottom_nav.selectedItemId = R.id.menu_headline


        // & this code is of external library we added for bottom navigation bar
        bottom_nav.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
            override fun onItemSelected(id: Int) {

                lateinit var currentFrag: Fragment

                when (id) {
                    R.id.menu_headline -> currentFrag = HomeFragment()
                    R.id.menu_science -> currentFrag = ScienceFragment()
                    R.id.menu_tech -> currentFrag = TechFragment()
                    R.id.menu_bbc -> currentFrag = BBCFragment()
                }
                supportFragmentManager.beginTransaction().replace(R.id.container, currentFrag).commit()
            }
        })

        bottom_nav.setItemSelected(R.id.menu_headline)

    }


//    override fun onStart() {
//        super.onStart()
//
//        if (checkPermission()) {
//            setUpRecyclerView()
//        }
//    }
//
//    fun checkPermission(): Boolean {
//
//        var internetPermission =
//            ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
//
////        val sampleArray = arrayOf(4,6,4,7,8,5,4)
//        val permissionList = ArrayList<String>()
//
//        if (internetPermission != PackageManager.PERMISSION_GRANTED) {
//            permissionList.add(Manifest.permission.INTERNET)
//        }
//
//        if (permissionList.isNotEmpty()) {
//            ActivityCompat.requestPermissions(this, permissionList.toTypedArray(), MY_PERMISSION_CODE)
//            return false
//        } else
//            return true
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == MY_PERMISSION_CODE) {
//
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                setUpRecyclerView()
//            }else{
//                var alertDialog = AlertDialog.Builder(this)
//                    .setCancelable(false)
//                    .setTitle("Permission Required")
//                    .setMessage("Internet permission is needed")
//                    .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
//                        checkPermission()
//                        dialogInterface.dismiss()
//                    })
//            }
//        }
//    }

}