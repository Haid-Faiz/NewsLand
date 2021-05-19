package com.example.newsapp.ui

import okhttp3.ResponseBody

sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorCode: Int?,
        val isNetworkError: Boolean? = null,
        val errorBody: ResponseBody?
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}