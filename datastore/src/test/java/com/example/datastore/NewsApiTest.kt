package com.example.datastore

import com.example.datastore.remote.NewsClient
import com.example.datastore.remote.apis.NewsApi
import com.example.datastore.remote.params.Category
import com.example.datastore.remote.params.Country
import com.example.datastore.remote.params.Source
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NewsApiTest {


    private val api = NewsClient().buildApi<NewsApi>(NewsApi::class.java)

    @Test
    fun `GET news by country`() {
        runBlocking {
            val response = api.getNewsByCountry(Country.INDIA, page = 1, pageSize = 10).body()?.articles
            assertNotNull(response)
        }
    }


    @Test
    fun `GET news by category`() {
        runBlocking {
            val response = api.getNewsByCategory(Category.TECHNOLOGY, page = 1, pageSize = 10).body()?.articles
            assertNotNull(response)
        }
    }

    @Test
    fun `GET news by sources`() {
        runBlocking {
            val response = api.getNewsBySources(Source.BBC, page = 1, pageSize = 10).body()?.articles
            assertNotNull(response)
        }
    }
}