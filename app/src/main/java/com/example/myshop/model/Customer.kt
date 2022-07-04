package com.example.myshop.model

import com.squareup.moshi.Json

data class Customer(

    @Json(name = "id")
    val id: Int?,

    @Json(name = "email")
    val email: String,

    @Json(name = "first_name")
    val first_name:String,
//    val last_name: String,
//    val role: String,

    @Json(name = "username")
    val username :String,

    @Json(name = "password")
    val password: String?,
//    val avatar_url: String
)