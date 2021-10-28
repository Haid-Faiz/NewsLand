package com.example.newsapp.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.datastore.remote.models.Article
import com.example.datastore.remote.params.Category
import com.example.datastore.remote.params.Country
import com.example.datastore.remote.params.Source
import com.example.newsapp.data.BaseViewModel
import com.example.newsapp.data.repositories.NewsRepo
import com.example.newsapp.utils.Constants.CATEGORY
import com.example.newsapp.utils.Constants.SOURCES
import com.example.newsapp.utils.Constants.TOP_HEADLINES
import com.example.newsapp.utils.Util
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow

// @HiltViewModel
class NewsFeedViewModel @AssistedInject constructor(
    util: Util,
    private val newsRepo: NewsRepo,
    @Assisted private val newsType: String,
    @Assisted private val tabPosition: Int
) : BaseViewModel(newsRepo) {

    @AssistedFactory
    interface Factory {
        fun create(newsType: String, tabPosition: Int): NewsFeedViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun providesFactory(
            assistedFactory: Factory,
            newsType: String,
            tabPosition: Int
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(newsType, tabPosition) as T
            }
        }
    }

    var newsFeed: Flow<PagingData<Article>>? = null

    init {
        val list = util.getTabsTitle(newsType)
        when (newsType) {
            TOP_HEADLINES -> getNewsByCountry(util.toEnumCountry(list?.get(tabPosition)))
            CATEGORY -> getNewsByCategory(util.toEnumCategory(list?.get(tabPosition)))
            SOURCES -> getNewsBySources(util.toEnumSource(list?.get(tabPosition)))
        }
    }

    private fun getNewsByCountry(country: Country) {
        newsFeed = newsRepo.getNewsByCountry(country).cachedIn(viewModelScope)
//        return newsRepo.getNewsByCountry(country).cachedIn(viewModelScope)
    }

    private fun getNewsByCategory(category: Category) {
        newsFeed = newsRepo.getNewsByCategory(category).cachedIn(viewModelScope)
//        return newsRepo.getNewsByCategory(category).cachedIn(viewModelScope)
    }

    private fun getNewsBySources(source: Source) {
        newsFeed = newsRepo.getNewsBySources(source).cachedIn(viewModelScope)
//        return newsRepo.getNewsBySources(source).cachedIn(viewModelScope)
    }
}
