package com.example.newsapp.ui.news_feed

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libnews.NewsClient
import com.example.libnews.apis.NewsApi
import com.example.libnews.models.Article
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.data.repositories.NewsRepo
import kotlinx.coroutines.launch

class NewsFeedViewModel : ViewModel() {

    private val newsRepo = NewsRepo(newsApi = NewsClient.buildApi(NewsApi::class.java))
    var tabPosition: MutableLiveData<Int> = MutableLiveData()

    private var _article: MutableLiveData<List<Article>> = MutableLiveData()
    var article: LiveData<List<Article>> = _article


    fun getNewsByCountry(country: Country) = viewModelScope.launch {
        _article.postValue(newsRepo.getNewsByCountry(country))
    }

    fun getNewsByCategory(category: Category) = viewModelScope.launch {
        _article.postValue(newsRepo.getNewsByCategory(category))
    }

    fun getNewsBySources(source: Source) = viewModelScope.launch {
        _article.postValue(newsRepo.getNewsBySources(source))
    }
}