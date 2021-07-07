package com.example.newsapp.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.datastore.remote.models.Article
import com.example.datastore.remote.params.Category
import com.example.datastore.remote.params.Country
import com.example.datastore.remote.params.Source
import com.example.newsapp.data.repositories.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {

    fun getNewsByCountry(country: Country): Flow<PagingData<Article>> {
        return newsRepo.getNewsByCountry(country).cachedIn(viewModelScope)
    }

    fun getNewsByCategory(category: Category): Flow<PagingData<Article>> {
        return newsRepo.getNewsByCategory(category).cachedIn(viewModelScope)
    }

    fun getNewsBySources(source: Source): Flow<PagingData<Article>> {
        return newsRepo.getNewsBySources(source).cachedIn(viewModelScope)
    }

    fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return newsRepo.searchNews(searchQuery).cachedIn(viewModelScope)
    }

//----------------------------------- RoomDatabase Calls -------------------------------------------


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