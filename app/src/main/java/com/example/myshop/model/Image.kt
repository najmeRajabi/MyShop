package com.example.myshop.model

import com.squareup.moshi.Json

data class Image(

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "src")
    val src: String
)
