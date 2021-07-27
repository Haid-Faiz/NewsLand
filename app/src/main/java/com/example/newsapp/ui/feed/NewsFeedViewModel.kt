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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {

    lateinit var news: Flow<PagingData<Article>>

    // If device will get rotated then
    var isRotated: Boolean = false

    fun getNewsByCountry(country: Country) {
        news = newsRepo.getNewsByCountry(country).cachedIn(viewModelScope)
//        return newsRepo.getNewsByCountry(country).cachedIn(viewModelScope)
    }

    fun getNewsByCategory(category: Category) {
        news = newsRepo.getNewsByCategory(category).cachedIn(viewModelScope)
//        return newsRepo.getNewsByCategory(category).cachedIn(viewModelScope)
    }

    fun getNewsBySources(source: Source) {
        news = newsRepo.getNewsBySources(source).cachedIn(viewModelScope)
//        return newsRepo.getNewsBySources(source).cachedIn(viewModelScope)
    }

    fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return newsRepo.searchNews(searchQuery).cachedIn(viewModelScope)
    }

// ----------------------------------- RoomDatabase Calls -------------------------------------------

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
