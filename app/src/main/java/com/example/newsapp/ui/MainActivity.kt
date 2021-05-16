package com.example.newsapp.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.broadcast.MyBroadcastReceiver
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MyBroadcastReceiver.ConnectivityListener {
    //
//    lateinit var nightModePref: SharedPreferences
//    lateinit var editor: SharedPreferences.Editor
    var isNightModeOn: Boolean = false
//    lateinit var myBroadcastReceiver: MyBroadcastReceiver
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setSupportActionBar(_binding?.appBarMain?.toolbar)
        setUpNav()
//         Instantiating MyBroadcastReceiver
//        myBroadcastReceiver = MyBroadcastReceiver()
        val navHeaderView = _binding?.navView?.getHeaderView(0)
        navHeaderView?.findViewById<ImageView>(R.id.night_mode_Button)?.setOnClickListener {
            if (isNightModeOn) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                    bottom_nav.setItemSelected(R.id.menu_headline)
//                    editor.putBoolean("NightMode", false).apply()
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                    bottom_nav.setItemSelected(R.id.menu_headline)
//                    editor.putBoolean("NightMode", true).apply()
            }
        }
    }

    private fun setUpNav() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
//                R.id.nav_search,
//                R.id.nav_saved,
                R.id.nav_top_headlines,
                R.id.nav_category,
                R.id.nav_sources
            ),
            _binding?.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        _binding?.appBarMain?.contentMain?.navBottom?.setupWithNavController(navController)
        _binding?.navView?.setupWithNavController(navController)
    }

//    override fun onStart() {
//        super.onStart()
//        val intentFilter = IntentFilter()
//        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
//        registerReceiver(myBroadcastReceiver, intentFilter)
//    }

//    override fun onStop() {
//        super.onStop()
//        this.unregisterReceiver(myBroadcastReceiver)
//    }

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

//    private fun checkNightMode() {
//        nightModePref = getSharedPreferences("NightModePref", MODE_PRIVATE)
//        editor = nightModePref.edit()
//        isNightModeOn = nightModePref.getBoolean("NightMode", false)
//
//        if (isNightModeOn)
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//        else
//            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
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