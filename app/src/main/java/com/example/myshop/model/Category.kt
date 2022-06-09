package com.example.myshop.model

import com.squareup.moshi.Json

data class Category(

    @Json(name = "id")
    val id: Int,

    @Json(name = "name")
    val name: String,

    @Json(name = "image")
    val image: Image?
)
