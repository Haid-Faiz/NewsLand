package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.ui.feed.NewsFeedViewModel
import com.example.newsapp.utils.PreferenceRepository
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.*
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository
    private var isNightMode: Boolean = false
    private val viewModel: NewsFeedViewModel by viewModels()   // It's injection will take care by viewModels() property delegate
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var _binding: ActivityMainBinding? = null
    private lateinit var onDestinationChangedListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding!!.root)
        setSupportActionBar(_binding?.appBarMain?.toolbar)
        setUpNav()
//        checkNightMode()
        preferenceRepository.isNightMode.asLiveData().observe(this) {
            isNightMode = it ?: false
            if (it == true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        onDestinationChangedListener =
            NavController.OnDestinationChangedListener { controller, destination, arguments ->
                Log.d("navC", "onCreate: ${destination.id}")

                (destination.id != R.id.nav_search && destination.id != R.id.nav_saved).let {
                    _binding?.appBarMain?.contentMain?.navBottom?.isVisible = it
                    val p =
                        _binding?.appBarMain?.toolbar?.layoutParams as AppBarLayout.LayoutParams
                    if (it) {
                        p.scrollFlags = SCROLL_FLAG_SCROLL
                        // p.scrollFlags = SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
                    } else p.scrollFlags = SCROLL_FLAG_NO_SCROLL

                    _binding?.appBarMain?.toolbar?.layoutParams = p
                }
            }

//         Instantiating MyBroadcastReceiver
//        myBroadcastReceiver = MyBroadcastReceiver()
        val navHeaderView = _binding?.navView?.getHeaderView(0)
        navHeaderView?.findViewById<ImageView>(R.id.night_mode_Button)?.setOnClickListener {
            viewModel.setNightMode(!isNightMode)
        }
    }



//    private fun checkNightMode(): Boolean {
//
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_bottom_menu, menu)
        _binding?.appBarMain?.contentMain?.navBottom?.setupWithNavController(menu!!, navController)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setUpNav() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                // R.id.nav_search,
                // R.id.nav_saved,
                R.id.nav_top_headlines,
                R.id.nav_category,
                R.id.nav_sources
            ),
            _binding?.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // _binding?.appBarMain?.contentMain?.navBottom?.setupWithNavController(navController)
        _binding?.navView?.setupWithNavController(navController)
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

    override fun onStart() {
        super.onStart()
        navController.addOnDestinationChangedListener(onDestinationChangedListener)
    }

    override fun onStop() {
        super.onStop()
        navController.removeOnDestinationChangedListener(onDestinationChangedListener)
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
//Do you want to uninstall the existing application?