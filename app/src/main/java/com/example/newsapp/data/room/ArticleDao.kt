package com.example.newsapp.data.room

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: ArticleEntity): Long   // id which was inserted

    @Query("SELECT * FROM article_table")
    fun getArticles(): PagingSource<Int, ArticleEntity> // It's not suspend fun because LiveData/Flow by default works asynchronously

    @Delete
    suspend fun delete(article: ArticleEntity)
}