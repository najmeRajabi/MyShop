package com.example.myshop.data.network

import com.example.myshop.model.*
import retrofit2.Response
import retrofit2.http.*

private const val KEY = "ck_4e34eda4882e044742e643e35060ab5234231ab3"
private const val SECRET = "cs_28f528089254ed3dc6bc8cbd0ffeb34a52f69547"
interface ApiService {

    @GET("products")
    suspend fun getLastProducts(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("per_page") perPage: Int = 100
    ): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): Product

    @GET("products")
    suspend fun getMostSeenProducts(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("orderby") orderby : String = "rating",
        @Query("per_page") perPage: Int = 100

    ): List<Product>

    @GET("products")
    suspend fun getFavoriteProducts(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("orderby") orderby : String ="popularity",
        @Query("per_page") perPage: Int = 100

    ): List<Product>

    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET
    ): List<Category>

    @GET("products/categories/{id}")
    suspend fun getProductList(
        @Path("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): List<Product>

    @GET("products/")
    suspend fun getProductsByCategory(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("category") categoryId: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 100
    ): List<Product>

    @GET("products/")
    suspend fun searchInProducts(
        @Query("search") searchKey : String,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): List<Product>

    @GET("products/")
    suspend fun sortProducts(
        @Query("orderby") orderby : String ,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): List<Product>

    @Headers("Content-Type: application/json")
    @POST("orders/")
    suspend fun createOrder(
        @Body order: Order,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):Response<Order>

    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Body order: Order,
        @Query("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):Response<Order>

    @GET("orders/")
    suspend fun retrieveOrder(
        @Query("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):List<Order>

    // customer

    @POST("customers/")
    suspend fun register(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Body customer: Customer
    ): List<Customer>

    @GET("customers/{id}")
    suspend fun login(
        @Query("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): Customer

    @GET("products/reviews/")
    suspend fun retrieveReview(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        ):List<Review>
}