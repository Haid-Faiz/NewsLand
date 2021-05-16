package com.example.newsapp

object Utils {

    fun getTabsTitle(newsFeedName: String?): ArrayList<String>? {

        return when (newsFeedName) {
            "top_headlines" -> arrayListOf("India", "US", "UK", "Canada", "Singapore")
            "category" -> arrayListOf("Tech", "Health", "Entertainment", "Science", "Business")
            "sources" -> arrayListOf("BBC", "Times of India", "The Hindu", "CNN", "Al-Jazeera")
            else -> null
        }
    }
}