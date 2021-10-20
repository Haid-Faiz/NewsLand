package com.example.datastore.remote.params

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
enum class Country {

    @Json(name = "in")
    INDIA,

    @Json(name = "us")
    USA,

    @Json(name = "gb")
    UNITED_KINGDOM,

    @Json(name = "sg")
    SINGAPORE,

    @Json(name = "ca")
    CANADA
}
