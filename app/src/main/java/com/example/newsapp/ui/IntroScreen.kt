package com.example.newsapp.ui

import android.content.Intent
import android.content.SharedPreferences
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
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        saveIntroducedSession()
    }

    override fun onSkipPressed(currentFragment: Fragment?) {
        super.onSkipPressed(currentFragment)
        saveIntroducedSession()
    }

    private fun saveIntroducedSession() {
        editor.putBoolean("isIntroduced", true)
        editor.apply()
        startActivity(Intent(this@IntroScreen, MainActivity::class.java))
        finish()
    }
}