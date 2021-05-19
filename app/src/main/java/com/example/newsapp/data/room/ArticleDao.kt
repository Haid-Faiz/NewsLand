package com.example.newsapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity) : Long   // id which was inserted

    @Query("SELECT * FROM article_table")
    fun getArticles(): LiveData<List<ArticleEntity>> // It's not suspend fun because suspend fun doesn't work with LiveData

    @Delete
    suspend fun delete(article: ArticleEntity)
}