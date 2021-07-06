package com.example.newsapp.utils

import android.content.Context
import com.example.datastore.remote.params.Category
import com.example.datastore.remote.params.Country
import com.example.datastore.remote.params.Source
import com.example.newsapp.R

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
}