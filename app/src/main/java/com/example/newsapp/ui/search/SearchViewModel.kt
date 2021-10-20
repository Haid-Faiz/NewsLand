package com.example.newsapp.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.datastore.remote.models.Article
import com.example.newsapp.data.BaseViewModel
import com.example.newsapp.data.repositories.NewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val newsRepo: NewsRepo
) : BaseViewModel(newsRepo) {

    var news: Flow<PagingData<Article>>? = null

    fun searchNews(searchQuery: String) {
        news = newsRepo.searchNews(searchQuery).cachedIn(viewModelScope)
    }
}