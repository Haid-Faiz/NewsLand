package com.example.newsapp.ui

sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorCode: Int? = null,
        val message: String? = null,
        val isNetworkError: Boolean? = null
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}