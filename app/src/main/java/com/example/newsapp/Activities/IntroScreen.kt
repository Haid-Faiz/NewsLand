package com.example.newsapp.Activities

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.newsapp.R
import com.github.appintro.*

class IntroScreen : AppIntro() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("AppIntroCheck", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        showStatusBar(true)
        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.primary_text),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.colorPrimary)
        )
        setColorDoneText(ContextCompat.getColor(this, R.color.primary_text))
        setColorSkipButton(ContextCompat.getColor(this, R.color.primary_text))
        setSeparatorColor(ContextCompat.getColor(this, R.color.whiteColor))
        setNextArrowColor(R.color.primary_text)
//      setTransformer(AppIntroPageTransformerType.Depth)

        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_one))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_two))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_three))

//        askForPermissions(
//            permissions = arrayOf(
//                Manifest.permission.INTERNET,
//                Manifest.permission.ACCESS_NETWORK_STATE
//            ),
//            slideNumber = 3,
//            required = true)

    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        editor.putBoolean("isIntroduced", true)
        editor.apply()
        startActivity(Intent(this@IntroScreen, MainActivity::class.java))
        finish()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        editor.putBoolean("isIntroduced", true)
        editor.apply()
        startActivity(Intent(this@IntroScreen, MainActivity::class.java))
        finish()
    }

//    override fun onUserDeniedPermission(permissionName: String) {
//        // User pressed "Deny" on the permission dialog
//    }
//    override fun onUserDisabledPermission(permissionName: String) {
//        // User pressed "Deny" + "Don't ask again" on the permission dialog
//    }
}