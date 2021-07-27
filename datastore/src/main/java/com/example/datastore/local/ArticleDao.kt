package com.example.datastore.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.datastore.remote.models.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article): Long // id which was inserted

    @Query("SELECT * FROM article_table")
    fun getArticles(): PagingSource<Int, Article> // It's not suspend fun because LiveData/Flow by default works asynchronously

    @Delete
    suspend fun delete(article: Article)
}
