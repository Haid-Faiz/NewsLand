package com.example.newsapp.data.repositories

import com.example.libnews.apis.NewsApi
import com.example.libnews.models.Article
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source

class NewsRepo(private val newsApi: NewsApi) : BaseRepo() {

    suspend fun getNewsByCountry(country: Country): List<Article>? {
        return newsApi.getNewsByCountry(country).body()?.articles
    }

    suspend fun getNewsByCategory(category: Category): List<Article>? {
        return newsApi.getNewsByCategory(category).body()?.articles
    }

    suspend fun getNewsBySources(source: Source): List<Article>? {
        return newsApi.getNewsBySources(source).body()?.articles
    }

}
