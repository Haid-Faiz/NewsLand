package com.example.newsapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.libnews.apis.NewsApi
import com.example.libnews.models.Article
import com.example.libnews.models.NewsResponse
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.utils.Constants.STARTING_PAGE_INDEX
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class CountryPagingSource(
    private val newsApi: NewsApi,
    private val country: Country
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response: Response<NewsResponse> = newsApi.getNewsByCountry(
                country = country,
                page = page,
                pageSize = params.loadSize
            )
            val data = response.body()?.articles
            LoadResult.Page(
                data = data!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
        catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

}

class CategoryPagingSource(
    private val newsApi: NewsApi,
    private val category: Category
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response: Response<NewsResponse> = newsApi.getNewsByCategory(
                category = category,
                page = page,
                pageSize = params.loadSize
            )
            val data = response.body()?.articles
            LoadResult.Page(
                data = data!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
        catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

}

class SourcesPagingSource(
    private val newsApi: NewsApi,
    private val source: Source
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response: Response<NewsResponse> = newsApi.getNewsBySources(
                source = source,
                page = page,
                pageSize = params.loadSize
            )
            val data = response.body()?.articles
            LoadResult.Page(
                data = data!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
        catch (e: IOException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

}

class SearchPagingSource(
    private val newsApi: NewsApi,
    private val searchQuery: String
) : PagingSource<Int, Article>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {

        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response: Response<NewsResponse> = newsApi.searchNews(
                searchQuery = searchQuery,
                page = page,
                pageSize = params.loadSize
            )
            val data = response.body()?.articles
            LoadResult.Page(
                data = data!!,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
        catch (e: IOException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        TODO("Not yet implemented")
    }

}