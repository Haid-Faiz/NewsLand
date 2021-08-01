package com.example.newsapp.data.repositories

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.newsapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepo(private val applicationContext: Context) {
    // fun to handle Api error
    suspend fun <T> safeApiCall(
        api: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                if (isInternetAvailable()) {
                    val response: Response<T> = api()
                    if (response.isSuccessful) Resource.Success<T>(response.body()!!)
                    else Resource.Error(message = response.message())
                } else Resource.Error(message = "Please turn on your internet connection")
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> Resource.Error(
                        message = e.message ?: "Something went wrong"
                    )
                    is IOException -> Resource.Error(message = e.message ?: "Network failure")
                    else -> Resource.Error("Oops..! Something went wrong")
                }
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
                ?: return false // returning false out of this function
        ) ?: return false // returning false out of this function
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            else -> false
        }
    }
}
