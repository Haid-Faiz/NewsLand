package com.example.newsapp.Activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashScreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = getSharedPreferences("AppIntroCheck", MODE_PRIVATE)
        val isIntroduced = sharedPreferences.getBoolean("isIntroduced", false)

        if (isIntroduced)
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
        else
            startActivity(Intent(this@SplashScreen, IntroScreen::class.java))

        finish()
    }
}