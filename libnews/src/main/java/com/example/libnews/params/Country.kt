package com.example.libnews.params

import com.squareup.moshi.Json

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
