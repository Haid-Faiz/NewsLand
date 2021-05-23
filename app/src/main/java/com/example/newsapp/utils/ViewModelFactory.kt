package com.example.newsapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.ui.feed.NewsFeedViewModel

class ViewModelFactory(private val newsRepo: NewsRepo) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(NewsFeedViewModel::class.java) -> NewsFeedViewModel(newsRepo) as T
            else -> throw IllegalArgumentException("Your ViewModel class is not found")
        }
    }
}