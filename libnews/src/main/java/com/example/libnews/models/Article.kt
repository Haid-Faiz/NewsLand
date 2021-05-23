package com.example.libnews.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Article(
    val id: Int? = null,  // This param is for id for Room
    @Json(name = "author")
    val author: String?,
    @Json(name = "content")
    val content: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "publishedAt")
    val publishedAt: String,
    @Json(name = "source")
    val source: Source,
    @Json(name = "title")
    val title: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "urlToImage")
    val urlToImage: String?
) {
    @JsonClass(generateAdapter = true)
    data class Source(
        @Json(name = "id")
        val id: String?,
        @Json(name = "name")
        val name: String
    )
}
