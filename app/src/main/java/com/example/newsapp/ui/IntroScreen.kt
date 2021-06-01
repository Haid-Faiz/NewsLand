package com.example.newsapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.R
import com.example.newsapp.utils.PreferenceRepository
import com.github.appintro.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroScreen : AppIntro() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkIsOnBoarded()
        showStatusBar(true)
        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.primary_text),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.colorPrimary)
        )
        setColorDoneText(ContextCompat.getColor(this, R.color.primary_text))
        setColorSkipButton(ContextCompat.getColor(this, R.color.primary_text))
        setSeparatorColor(ContextCompat.getColor(this, R.color.whiteColor))
        setNextArrowColor(R.color.primary_text)
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_one))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_two))
        addSlide(AppIntroCustomLayoutFragment.newInstance(R.layout.intro_screen_three))
    }

    private fun checkIsOnBoarded() {
        preferenceRepository.isOnboarded.asLiveData().observe(this) {
            if (it) {
                startActivity(Intent(this@IntroScreen, MainActivity::class.java))
                finish()
            }
        }
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
        lifecycleScope.launch { preferenceRepository.setOnBoard(true) }
        startActivity(Intent(this@IntroScreen, MainActivity::class.java))
        finish()
    }

}