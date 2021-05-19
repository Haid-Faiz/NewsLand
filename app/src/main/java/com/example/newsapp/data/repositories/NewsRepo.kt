package com.example.newsapp.data.repositories

import com.example.libnews.apis.NewsApi
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.ui.Resource

class NewsRepo(private val newsApi: NewsApi) : BaseRepo() {

    suspend fun getNewsByCountry(country: Country, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsByCountry(country, pageNum) }


    suspend fun getNewsByCategory(category: Category, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsByCategory(category, pageNum) }


    suspend fun getNewsBySources(source: Source, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsBySources(source, pageNum) }


    suspend fun searchNews(searchQuery: String, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.searchNews(searchQuery, pageNum) }

}
