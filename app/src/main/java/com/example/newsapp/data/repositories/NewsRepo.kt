package com.example.newsapp.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.libnews.apis.NewsApi
import com.example.libnews.models.Article
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.data.room.ArticleDao
import com.example.newsapp.data.room.ArticleEntity
import com.example.newsapp.ui.Resource
import com.example.newsapp.utils.Util
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NewsRepo @Inject constructor(
    @ApplicationContext private val application: Context,
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao
) : BaseRepo(application) {         // Now, no need to create the Provides fun for NewsRepo

    //-------------------------------Remote Api calls-----------------------------------------------
    suspend fun getNewsByCountry(country: Country, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsByCountry(country, pageNum) }


    suspend fun getNewsByCategory(category: Category, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsByCategory(category, pageNum) }


    suspend fun getNewsBySources(source: Source, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.getNewsBySources(source, pageNum) }


    suspend fun searchNews(searchQuery: String, pageNum: Int): Resource<NewsResponse> =
        safeApiCall { newsApi.searchNews(searchQuery, pageNum) }

//----------------------------- Room Database calls ------------------------------------------------

    suspend fun insert(article: Article) {
        val articleEntity = Util.toInsertArticleEntity(article = article)
        // this fun will update the article also
        articleDao.insert(articleEntity)
    }

    fun getAllNewsList(): LiveData<List<ArticleEntity>> =
        articleDao.getArticles()

    suspend fun delete(article: Article) {
        return articleDao.delete(Util.toDeleteArticleEntity(article))
    }
}
