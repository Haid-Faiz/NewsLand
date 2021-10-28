package com.example.newsapp.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.datastore.remote.models.Article
import com.example.newsapp.data.repositories.NewsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

open class BaseViewModel(
    private val newsRepo: NewsRepo
) : ViewModel() {

    fun insert(article: Article) = viewModelScope.launch {
        newsRepo.insert(article)
    }

    fun getAllNewsList(): Flow<PagingData<Article>> =
        newsRepo.getAllNewsList().cachedIn(viewModelScope)

    fun delete(article: Article) = viewModelScope.launch {
        newsRepo.delete(article)
    }

    fun setNightMode(nightMode: Boolean) = viewModelScope.launch {
        newsRepo.setNightMode(nightMode)
    }
}
