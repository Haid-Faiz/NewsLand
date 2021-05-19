package com.example.newsapp.data.repositories

import com.example.newsapp.ui.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo {
    // fun to handle Api error
    suspend fun <T> safeApiCall(
        api: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = api()
                if (response.isSuccessful) Resource.Success<T>(response.body()!!)
                else Resource.Failure(
                    response.code(),
                    isNetworkError = false,
                    errorBody = response.errorBody()
                )
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> Resource.Failure(e.code(), false, e.response()?.errorBody())
                    is IOException -> Resource.Failure(null, true, null)
                    else -> Resource.Failure(null, null, null)
                }
            }
        }
    }
}