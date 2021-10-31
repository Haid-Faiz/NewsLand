package com.example.newsapp.ui

import android.os.Build
import android.os.Bundle
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
import com.example.newsapp.ui.search.SearchViewModel
import com.example.newsapp.utils.PreferenceRepository
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
import com.google.android.material.appbar.AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository // This is Field injection
    private var _isNightMode: Boolean = false
    private val viewModel: SearchViewModel by viewModels() // It's injection will take care by viewModels() property delegate
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var onDestinationChangedListener: NavController.OnDestinationChangedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        setUpNav()
        checkNightMode()

        onDestinationChangedListener =
            NavController.OnDestinationChangedListener { _, destination, _ ->

                (destination.id != R.id.nav_search && destination.id != R.id.nav_saved).let {
                    binding.appBarMain.contentMain.navBottom.isVisible = it
                    val p =
                        binding.appBarMain.toolbar.layoutParams as AppBarLayout.LayoutParams
                    if (it) {
                        p.scrollFlags = SCROLL_FLAG_SCROLL
                        // p.scrollFlags = SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED
                    } else p.scrollFlags = SCROLL_FLAG_NO_SCROLL

                    binding.appBarMain.toolbar.layoutParams = p
                }
            }

        val navHeaderView = binding.navView.getHeaderView(0)
        navHeaderView?.findViewById<ImageView>(R.id.night_mode_Button)?.setOnClickListener {
            viewModel.setNightMode(!_isNightMode)
        }
    }

    private fun checkNightMode() {
        preferenceRepository.isNightMode.asLiveData().observe(this) {

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                // Running Android OS version is less than Android 10 i.e. Android Q
                _isNightMode = it ?: false   // Updating _isNightMode value
                if (_isNightMode)
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                // Running Android OS version is >= Android 10 i.e. Android Q
                _isNightMode = it ?: false
                // Now check weather preferenceRepository.isNightMode is returning null or not
                // if it is null -> Follow system mode else follow saved night mode status
                if (it == null) {
                    // Follow system mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                } else {
                    if (it)
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.nav_bottom_menu, menu)
        binding.appBarMain.contentMain.navBottom.setupWithNavController(menu!!, navController)
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
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // _binding?.appBarMain?.contentMain?.navBottom?.setupWithNavController(navController)
        binding.navView.setupWithNavController(navController)
    }

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
}
