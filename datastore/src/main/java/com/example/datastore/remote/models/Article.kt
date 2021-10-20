package com.example.datastore.remote.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@Entity(tableName = "article_table")
@JsonClass(generateAdapter = true)
data class Article(
//    @PrimaryKey(autoGenerate = true)
//    val id: Int? = null,
    @PrimaryKey(autoGenerate = false)
    @Json(name = "url")
    val url: String,
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
    @Json(name = "urlToImage")
    val urlToImage: String?
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Source(
        @Json(name = "id")
        val id: String?,
        @Json(name = "name")
        val name: String
    )
}
