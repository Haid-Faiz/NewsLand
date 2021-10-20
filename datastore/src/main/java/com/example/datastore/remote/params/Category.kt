package com.example.datastore.remote.params

import androidx.annotation.Keep
import com.squareup.moshi.Json

@Keep
enum class Category {

    @Json(name = "technology")
    TECHNOLOGY,

    @Json(name = "health")
    HEALTH,

    @Json(name = "science")
    SCIENCE,

    @Json(name = "entertainment")
    ENTERTAINMENT,

    @Json(name = "business")
    BUSINESS
}
