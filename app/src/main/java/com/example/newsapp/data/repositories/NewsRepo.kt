package com.example.newsapp.data.repositories

import com.example.libnews.apis.NewsApi
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.ui.Resource

class NewsRepo(private val newsApi: NewsApi) : BaseRepo() {

    suspend fun getNewsByCountry(country: Country): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsByCountry(country) }


    suspend fun getNewsByCategory(category: Category): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsByCategory(category) }


    suspend fun getNewsBySources(source: Source): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsBySources(source) }

}
