package com.example.datastore.remote

import com.example.datastore.BuildConfig
import com.example.datastore.remote.converters.EnumConverterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class NewsClient {

//        val newsURL = "https://newsapi.org/v2/top-headlines?country=in&apiKey=####"

    private val BASE_URL = "https://newsapi.org/v2/"

//    val logging = HttpLoggingInterceptor()
//    logging.setLevel(HttpLoggingInterceptor.Level.BODY)

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .addConverterFactory(EnumConverterFactory())
            .build()
    }

    private val okHttpClient by lazy {
        OkHttpClient.Builder().addInterceptor(
            Interceptor {
                val request = it.request().newBuilder()
                    .header("Authorization", BuildConfig.Api_Key)
                    .build()
                it.proceed(request)
            }
        ).build()
    }

    fun <T> buildApi(api: Class<T>): T = retrofit.create(api)
}
