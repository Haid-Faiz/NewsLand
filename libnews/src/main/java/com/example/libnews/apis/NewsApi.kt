package com.example.libnews.apis

import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNewsByCountry(
        @Query("country") country: Country
    ): Response<NewsResponse>

    @GET("top-headlines?country=in")
    suspend fun getNewsByCategory(
        @Query("category") category: Category
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getNewsBySources(
        @Query("sources") source: Source
    ) : Response<NewsResponse>
}


