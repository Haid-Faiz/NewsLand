package com.example.datastore.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.datastore.remote.models.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class NewsDatabase : RoomDatabase() {

    // fun that return dao object
    abstract fun getArticleDao(): ArticleDao

//    companion object {
//
//        @Volatile
//        private var instance: NewsDatabase? = null
//
//        operator fun invoke(context: Context): NewsDatabase = instance ?: synchronized(this) {
//            instance ?: createDatabase(context).also {
//                instance = it
//            }
//        }
//
//        private fun createDatabase(context: Context): NewsDatabase = Room.databaseBuilder(
//            context.applicationContext,
//            NewsDatabase::class.java,
//            NEWS_APP_DATABASE_NAME
//        ).build()
//    }
}
