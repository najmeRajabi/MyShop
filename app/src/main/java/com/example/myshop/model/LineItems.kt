package com.example.myshop.model

data class LineItems(
    val id: Int,
    val product_id : Int,
    val quantity: Int,
    val subtotal: String, // before discount
    val total: String, // after discount
    val price: String
)
