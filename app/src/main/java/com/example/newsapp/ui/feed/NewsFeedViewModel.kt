package com.example.newsapp.ui.feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libnews.models.Article
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.data.room.ArticleEntity
import com.example.newsapp.ui.Resource
import com.example.newsapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NewsFeedViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    var tabPosition: MutableLiveData<Int> = SingleLiveEvent()

    // News Article LiveData
    private var _article: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var article: LiveData<Resource<NewsResponse>> = _article
    private val pageNum = 1

    // Search News Article LiveData
    private var _searchArticle: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchArticle: LiveData<Resource<NewsResponse>> = _searchArticle
    private val searchPageNum = 1

    fun getNewsByCountry(country: Country) = viewModelScope.launch {
        Log.d("callerrrr1", "getNewsByCountry: ")
        _article.postValue(Resource.Loading)
        _article.postValue(newsRepo.getNewsByCountry(country, pageNum))
    }

    fun getNewsByCategory(category: Category) = viewModelScope.launch {
        Log.d("callerrrr2", "getNewsByCategory: ")
        _article.postValue(Resource.Loading)
        _article.postValue(newsRepo.getNewsByCategory(category, pageNum))
    }

    fun getNewsBySources(source: Source) = viewModelScope.launch {
        Log.d("callerrrr3", "getNewsBySource: ")
        _article.postValue(Resource.Loading)
        _article.postValue(newsRepo.getNewsBySources(source, pageNum))
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        Log.d("callerrrr4", "getSearchNews: ")
        _searchArticle.postValue(Resource.Loading)
        _searchArticle.postValue(newsRepo.searchNews(searchQuery, searchPageNum))
    }

//----------------------------------- RoomDatabase Calls -------------------------------------------


    fun insert(article: Article) = viewModelScope.launch {
        newsRepo.insert(article)
    }

    fun getAllNewsList(): LiveData<List<ArticleEntity>> = newsRepo.getAllNewsList()

    fun delete(article: Article) = viewModelScope.launch {
        newsRepo.delete(article)
    }
}