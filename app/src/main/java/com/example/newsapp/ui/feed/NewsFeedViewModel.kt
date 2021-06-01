package com.example.newsapp.ui.feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.libnews.models.Article
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.data.room.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsFeedViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : ViewModel() {

//    private var numList = mutableListOf<Int>()
//    var tabPosition: Flow<Int> = _tabPosition.filter { newNum ->
//
//        numList.contains(newNum).let { isContain ->
//            if (isContain) false else {
//                numList.add(newNum)
//                true
//            }
//        }
//    }

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

    fun getAllNewsList(): LiveData<List<ArticleEntity>> = newsRepo.getAllNewsList()

    fun delete(article: Article) = viewModelScope.launch {
        newsRepo.delete(article)
    }

    fun setNightMode(nightMode: Boolean) = viewModelScope.launch {
        newsRepo.setNightMode(nightMode)
    }

}