package com.example.newsapp.utils

sealed class Resource<T>(status: Status, data: T? = null, message: String? = null) {

    class Success<T>(data: T) : Resource<T>(status = Status.SUCCESS, data = data)

    class Error<T>(message: String) : Resource<T>(status = Status.ERROR, message = message)

    class Loading<T>(data: T? = null) : Resource<T>(status = Status.LOADING)
    // Added data in loading also because sometimes
    // we may need to send some data in loading state
    // also. Like: during loading/refreshing i want
    // to show this data, etc.
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}
