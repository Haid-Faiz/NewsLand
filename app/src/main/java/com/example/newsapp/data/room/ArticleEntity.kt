package com.example.newsapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.libnews.models.Article

@Entity(tableName = "article_table")
class ArticleEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val source: Article.Source,
    val title: String,
    val url: String,
    val urlToImage: String?
)