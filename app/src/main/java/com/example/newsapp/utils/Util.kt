package com.example.newsapp.utils

import android.content.Context
import com.example.libnews.models.Article
import com.example.libnews.params.Category
import com.example.libnews.params.Country
import com.example.libnews.params.Source
import com.example.newsapp.R
import com.example.newsapp.data.room.ArticleEntity

class Util(val context: Context) {

    fun getTabsTitle(newsFeedName: String?): ArrayList<String>? = when (newsFeedName) {
        "top_headlines" -> arrayListOf(
            getString(R.string.india),
            getString(R.string.usa),
            getString(R.string.united_kingdom),
            getString(R.string.canada),
            getString(R.string.singapore)
        )
        "category" -> arrayListOf(
            getString(R.string.technology),
            getString(R.string.health),
            getString(R.string.entertainment),
            getString(R.string.science),
            getString(R.string.business)
        )
        "sources" -> arrayListOf(
            getString(R.string.bbc),
            getString(R.string.times_of_india),
            getString(R.string.the_hindu),
            getString(R.string.cnn),
            getString(R.string.al_jazeera)
        )
        else -> null
    }

    fun toEnumCountry(country: String?): Country = when (country) {
        getString(R.string.india) -> Country.INDIA
        getString(R.string.usa) -> Country.USA
        getString(R.string.united_kingdom) -> Country.UNITED_KINGDOM
        getString(R.string.canada) -> Country.CANADA
        getString(R.string.singapore) -> Country.SINGAPORE
        else -> Country.INDIA
    }

    fun toEnumCategory(category: String?): Category = when (category) {
        getString(R.string.technology) -> Category.TECHNOLOGY
        getString(R.string.health) -> Category.HEALTH
        getString(R.string.entertainment) -> Category.ENTERTAINMENT
        getString(R.string.science) -> Category.SCIENCE
        getString(R.string.business) -> Category.BUSINESS
        else -> Category.TECHNOLOGY
    }

    fun toEnumSource(sources: String?): Source = when (sources) {
        getString(R.string.bbc) -> Source.BBC
        getString(R.string.times_of_india) -> Source.THE_TIMES_OF_INDIA
        getString(R.string.the_hindu) -> Source.THE_HINDU
        getString(R.string.cnn) -> Source.CNN
        getString(R.string.al_jazeera) -> Source.AL_JAZEERA
        else -> Source.BBC
    }

    private fun getString(string: Int): String {
        return context.getString(string)
    }

    companion object {
        fun toInsertArticleEntity(article: Article): ArticleEntity = ArticleEntity(
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            source = article.source,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )

        fun toDeleteArticleEntity(article: Article): ArticleEntity = ArticleEntity(
            id = article.id,
            author = article.author,
            content = article.content,
            description = article.description,
            publishedAt = article.publishedAt,
            source = article.source,
            title = article.title,
            url = article.url,
            urlToImage = article.urlToImage
        )

        fun toArticleList(list: List<ArticleEntity>?): ArrayList<Article> {

            val newList: ArrayList<Article> = ArrayList()

            list?.forEach {
                newList.add(
                    Article(
                        id = it.id,
                        author = it.author,
                        content = it.content,
                        description = it.description,
                        publishedAt = it.publishedAt,
                        source = it.source,
                        title = it.title,
                        url = it.url,
                        urlToImage = it.urlToImage
                    )
                )
            }
            return newList
        }
    }
}





















//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.net.ConnectivityManager
//import android.net.NetworkInfo
//
//class MyBroadcastReceiver : BroadcastReceiver() {
//
//    var isConnected: Boolean? = null
//    lateinit var connectivityListener: ConnectivityListener
//
//    override fun onReceive(context: Context, intent: Intent) {
//        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
//
//        connectivityListener = context as ConnectivityListener
//
//        if (intent == null || intent.extras == null)
//            return
//
//        val connectivityManager =
//            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val networkInfo = connectivityManager.activeNetworkInfo
//
//        if (networkInfo != null && networkInfo.state == NetworkInfo.State.CONNECTED)
//            isConnected =
//                networkInfo.type == ConnectivityManager.TYPE_MOBILE || networkInfo.type == ConnectivityManager.TYPE_WIFI
//        else
//            isConnected = false
//
//
//        connectivityListener.checkConnection(isConnected!!)
//    }
//
//    interface ConnectivityListener {
//        fun checkConnection(isConnected: Boolean)
//    }
//}