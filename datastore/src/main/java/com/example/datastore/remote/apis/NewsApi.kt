package com.example.datastore.remote.apis

import com.example.datastore.remote.models.NewsResponse
import com.example.datastore.remote.params.Category
import com.example.datastore.remote.params.Country
import com.example.datastore.remote.params.Source
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNewsByCountry(
        @Query("country") country: Country,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>

    @GET("top-headlines?country=in")
    suspend fun getNewsByCategory(
        @Query("category") category: Category,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getNewsBySources(
        @Query("sources") source: Source,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>


    @GET("everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<NewsResponse>

}


