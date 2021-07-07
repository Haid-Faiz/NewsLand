package com.example.newsapp.di

import android.content.Context
import androidx.room.Room
import com.example.datastore.remote.NewsClient
import com.example.datastore.remote.apis.NewsApi
import com.example.datastore.local.ArticleDao
import com.example.datastore.local.NewsDatabase
import com.example.newsapp.utils.Constants.NEWS_APP_DATABASE_NAME
import com.example.newsapp.utils.Util
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesNewsClient(): NewsClient = NewsClient()

    @Provides
    @Singleton
    fun providesNewsApi(newsClient: NewsClient): NewsApi = newsClient.buildApi(NewsApi::class.java)

    @Provides
    @Singleton
    fun providesNewsDatabase(
        @ApplicationContext application: Context
    ): NewsDatabase = Room.databaseBuilder(
        application,
        NewsDatabase::class.java,
        NEWS_APP_DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun providesArticleDao(newsDatabase: NewsDatabase): ArticleDao = newsDatabase.getArticleDao()

//    @Provides
//    @Singleton
//    fun providesAppPref(@ApplicationContext context: Context) = PreferenceRepository(context)

}