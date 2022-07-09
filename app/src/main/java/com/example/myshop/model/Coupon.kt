package com.example.myshop.model

data class Coupon(
    val id: Int ,
    val code : String,
    val discount : String? = null
)
