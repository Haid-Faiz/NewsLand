package com.example.libnews

import com.example.libnews.apis.NewsApi
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.runBlocking
import org.junit.Test

class NewsApiTest {


    val api = NewsClient.buildApi<NewsApi>(NewsApi::class.java)

    @Test
    fun `GET news by country`() {
        runBlocking {
            val response = api.getNewsByCountry(Country.INDIA).body()?.articles
            assertNotNull(response)
        }
    }


    @Test
    fun `GET news by category`() {
        runBlocking {
            val response = api.getNewsByCategory(Category.TECHNOLOGY).body()?.articles
            assertNotNull(response)
        }
    }

    @Test
    fun `GET news by sources`() {
        runBlocking {
            val response = api.getNewsBySources(Source.BBC).body()?.articles
            assertNotNull(response)
        }
    }
}