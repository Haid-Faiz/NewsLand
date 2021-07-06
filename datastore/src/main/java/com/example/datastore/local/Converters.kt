package com.example.datastore.local

import androidx.room.TypeConverter
import com.example.datastore.remote.models.Article

class Converters  {

    // We need type converters for Room because room doesn't understand custom type... it only
    // knows primitive types

    @TypeConverter
    fun sourceToString(source: Article.Source): String = source.name

    @TypeConverter
    fun stringToSource(string: String): Article.Source = Article.Source(string, string)
}