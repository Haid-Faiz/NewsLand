package com.example.newsapp.ui.news_feed

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.ui.Resource
import com.example.newsapp.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class NewsFeedViewModel(private val newsRepo: NewsRepo) : ViewModel() {

    var tabPosition: MutableLiveData<Int> = MutableLiveData()

    private var _article: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var article: LiveData<Resource<NewsResponse>> = _article

    fun getNewsByCountry(country: Country) = viewModelScope.launch {
        Log.d("callerrrr", "getNewsByCountry: ")
        _article.postValue(Resource.Loading)
        _article.postValue(newsRepo.getNewsByCountry(country))
    }

    fun getNewsByCategory(category: Category) = viewModelScope.launch {
        Log.d("callerrrr", "getNewsByCategory: ")
        _article.postValue(Resource.Loading)
        _article.postValue(newsRepo.getNewsByCategory(category))
    }

    fun getNewsBySources(source: Source) = viewModelScope.launch {
        Log.d("callerrrr", "getNewsBySource: ")
        _article.postValue(Resource.Loading)
        _article.postValue(newsRepo.getNewsBySources(source))
    }
}