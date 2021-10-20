package com.example.datastore.remote.params

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
enum class Source {

    @Json(name = "bbc-news")
    BBC,

    @Json(name = "the-times-of-india")
    THE_TIMES_OF_INDIA,

    @Json(name = "the-hindu")
    THE_HINDU,

    @Json(name = "al-jazeera-english")
    AL_JAZEERA,

    @Json(name = "cnn")
    CNN
}
