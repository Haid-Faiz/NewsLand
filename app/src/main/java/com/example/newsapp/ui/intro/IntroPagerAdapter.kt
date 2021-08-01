package com.example.newsapp.ui.intro

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieDrawable
import com.example.newsapp.R
import com.example.newsapp.databinding.IntroPageItemBinding

class IntroPagerAdapter : RecyclerView.Adapter<IntroPagerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            IntroPageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = 3

    inner class ViewHolder(
        private val binding: IntroPageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) = binding.apply {
            introAnim.playAnimation()
            introAnim.repeatCount = LottieDrawable.INFINITE
            when (position) {
                0 -> {
                    introTitle.text = "Welcome to NewsLand"
                    introDescription.text =
                        " Get latest news as: \n\n - Top headlines \n - Categorised News \n - News from different sources"
                    introAnim.setAnimation(R.raw.news_paper_anim)
                }
                1 -> {
                    introTitle.text = "Bookmark News"
                    introDescription.text =
                        "Save news locally and access them without internet connection"
                    introAnim.setAnimation(R.raw.anim_bookmark)
                }
                2 -> {
                    introTitle.text = "Search News"
                    introDescription.text = "Search any news you are looking for"
                    introAnim.setAnimation(R.raw.anim_search_news)
                }
            }
        }
    }
}
