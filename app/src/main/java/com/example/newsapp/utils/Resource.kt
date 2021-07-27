package com.example.newsapp.utils

sealed class Resource<out T> {

    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val errorCode: Int? = null,
        val message: String? = null,
        val isNetworkError: Boolean? = null
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}

// sealed class Resource<T>(data: T? = null, message: String? = null) {
//
//    class Success<T>(data: T) : Resource<T>(data = data)
//    class Error<T>(message: String) : Resource<T>(message = message)
//    class Loading<T>(data: T? = null) : Resource<T>()  // Added data in loading also because sometimes
//                                                       // we may need to send some data in loading state
//                                                       // also. Like: during loading/refreshing i want
//                                                       // to show this data, etc.
// }
