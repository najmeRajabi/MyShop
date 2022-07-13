package com.example.myshop.model

import com.squareup.moshi.Json

data class Review(

    @Json(name = "id")
    val id: Int,

    @Json(name = "reviewer")
    val reviewer : String,

    @Json(name = "review")
    val review : String,

    @Json(name = "product_id")
    val product_id : Int,

    @Json(name = "rating")
    val rating : Int
)

data class ReviewDelete (
    val deleted: Boolean
        )
