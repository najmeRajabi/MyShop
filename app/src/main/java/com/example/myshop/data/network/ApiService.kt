package com.example.myshop.data.network

import com.example.myshop.model.Product
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("products")
    suspend fun getProducts(
        @Query("consumer_key") key : String = "ck_4e34eda4882e044742e643e35060ab5234231ab3",
        @Query("consumer_secret") secret : String = "cs_28f528089254ed3dc6bc8cbd0ffeb34a52f69547",
    ): List<Product>

}