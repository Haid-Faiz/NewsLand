package com.example.newsapp.data.room

import androidx.room.TypeConverter
import com.example.libnews.models.Article

class Converters  {

    // We need type converters for Room because room doesn't understand custom type... it only
    // knows primitive types

    @TypeConverter
    fun sourceToString(source: Article.Source): String = source.name

    @TypeConverter
    fun stringToSource(string: String): Article.Source = Article.Source(string, string)
}