package com.example.newsapp.Activities

import android.content.IntentFilter
import android.content.SharedPreferences
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.BroadCast.MyBroadcastReceiver
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyBroadcastReceiver.ConnectivityListener {

    lateinit var nightModePref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var isNightModeOn: Boolean = false
    lateinit var myBroadcastReceiver: MyBroadcastReceiver

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setSupportActionBar(_binding?.appBarMain?.toolbar)
//         Instantiating MyBroadcastReceiver
        myBroadcastReceiver = MyBroadcastReceiver()

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        checkNightMode()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_search,
                R.id.nav_saved
            ),
            _binding?.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        _binding?.navView?.setupWithNavController(navController)
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
//        bottom_nav.setOnItemSelectedListener(object : ChipNavigationBar.OnItemSelectedListener {
//            override fun onItemSelected(id: Int) {
//
//                lateinit var currentFrag: Fragment
//                when (id) {
//                    R.id.menu_headline -> currentFrag = HomeFragment()
//                    R.id.menu_science -> currentFrag = ScienceFragment()
//                    R.id.menu_tech -> currentFrag = TechFragment()
//                    R.id.menu_bbc -> currentFrag = BBCFragment()
//                }
//
//                supportFragmentManager.beginTransaction().replace(R.id.container, currentFrag)
//                    .commit()
//            }
//        })
//
//        bottom_nav.setItemSelected(R.id.menu_headline)
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(myBroadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        this.unregisterReceiver(myBroadcastReceiver)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.night_mode_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.button_night_mode -> {

                if (isNightModeOn) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    bottom_nav.setItemSelected(R.id.menu_headline)
                    editor.putBoolean("NightMode", false).apply()
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    bottom_nav.setItemSelected(R.id.menu_headline)
                    editor.putBoolean("NightMode", true).apply()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun checkConnection(isConnected: Boolean) {
//        if (isConnected) {
//            container.visibility = View.VISIBLE
//            no_internet_anim.visibility = View.GONE
//            bottom_nav.setItemSelected(R.id.menu_headline)
//            supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment())
//                .commit()
//        } else {
//            container.visibility = View.GONE
//            no_internet_anim.visibility = View.VISIBLE
//        }
    }

    private fun checkNightMode() {
        nightModePref = getSharedPreferences("NightModePref", MODE_PRIVATE)
        editor = nightModePref.edit()
        isNightModeOn = nightModePref.getBoolean("NightMode", false)

        if (isNightModeOn)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


//The device already has an application with the same package but a different signature.
//In order to proceed, you will have to uninstall the existing application
//
//WARNING: Uninstalling will remove the application data!
//
//Do you want to uninstall the existing application?