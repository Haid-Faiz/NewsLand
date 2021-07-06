package com.example.datastore.remote.params

import com.squareup.moshi.Json

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