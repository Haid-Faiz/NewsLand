package com.example.newsapp.data.repositories

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.datastore.local.ArticleDao
import com.example.datastore.paging.CategoryPagingSource
import com.example.datastore.paging.CountryPagingSource
import com.example.datastore.paging.SearchPagingSource
import com.example.datastore.paging.SourcesPagingSource
import com.example.datastore.remote.apis.NewsApi
import com.example.datastore.remote.models.Article
import com.example.datastore.remote.params.Category
import com.example.datastore.remote.params.Country
import com.example.datastore.remote.params.Source
import com.example.newsapp.utils.Constants.PAGE_LOAD_SIZE
import com.example.newsapp.utils.PreferenceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepo @Inject constructor(
    @ApplicationContext private val application: Context,
    private val newsApi: NewsApi,
    private val articleDao: ArticleDao,
    private val preferenceRepository: PreferenceRepository
) : BaseRepo(application) { // Now, no need to create the Provides fun for NewsRepo

    // -------------------------------Remote Api calls-----------------------------------------------
//    suspend fun getNewsByCountry(country: Country, pageNum: Int): Resource<NewsResponse> =
//        safeApiCall { newsApi.getNewsByCountry(country, pageNum) }

    fun getNewsByCountry(country: Country): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_LOAD_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { CountryPagingSource(newsApi, country) }
        ).flow // flow & livedata are already asynchronous
    }

    fun getNewsByCategory(category: Category): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_LOAD_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { CategoryPagingSource(newsApi, category) }
        ).flow
    }

    fun getNewsBySources(source: Source): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_LOAD_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { SourcesPagingSource(newsApi, source) }
        ).flow
    }

    fun searchNews(searchQuery: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_LOAD_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { SearchPagingSource(newsApi, searchQuery) }
        ).flow
    }

// ----------------------------- Room Database calls ------------------------------------------------

    suspend fun insert(article: Article) {
        // this fun will update the article also
        articleDao.insert(article)
    }

    fun getAllNewsList(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { articleDao.getArticles() }
        ).flow
    }

    suspend fun delete(article: Article) {
        return articleDao.delete(article)
    }

    suspend fun setNightMode(nightMode: Boolean) = preferenceRepository.setNightMode(nightMode)

//    suspend fun isNightMode(): Boolean? = preferenceRepository.isNightMode
}
