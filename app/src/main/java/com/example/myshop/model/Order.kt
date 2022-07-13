package com.example.myshop.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class Order(

    @Json(name = "id")
    val id: Int,

//    @Json(name = "name")
//    val name: String
//    val number: String,
//    val status: String = "pending",
//    val customer_id: Int,
//    val total : String,
//    val billing : Billing,//پرداخت

    @Json(name = "line_items")
    var line_items: List<LineItems>,

//    val coupon_lines: List<Coupon?>? = null ,

    val discount_total: String? = null,

    val related_ids: List<Int>? = null,

    val customer_id: Int? = null

    ){
}

data class OrderCallback(
    val id: Int,
    val number: String,
    val currency: String,
    val total: String,
    val customer_id: Int,
    val line_items: List<LineItems>
)