package com.example.newsapp.ui.intro

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.newsapp.databinding.ActivityIntroBinding
import com.example.newsapp.ui.MainActivity
import com.example.newsapp.utils.PreferenceRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IntroActivity : AppCompatActivity() {

    @Inject
    lateinit var preferenceRepository: PreferenceRepository
    private var _binding: ActivityIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityIntroBinding.inflate(layoutInflater)
        checkIsOnBoarded()
        setContentView(binding.root)
        binding.apply {
            viewpager.adapter = IntroPagerAdapter()
            dotsIndicator.setupWithViewPager(binding.viewpager)
            buttonContinue.setOnClickListener {
                lifecycleScope.launch { preferenceRepository.setOnBoard(true) }
            }
        }
    }

    private fun checkIsOnBoarded() = preferenceRepository.isOnboarded.asLiveData().observe(this) {
        if (it) {
            startActivity(Intent(this@IntroActivity, MainActivity::class.java))
            finish()
        }
    }
}
