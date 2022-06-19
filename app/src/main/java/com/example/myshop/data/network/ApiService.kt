package com.example.myshop.data.network

import com.example.myshop.model.Category
import com.example.myshop.model.Product
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

private const val KEY = "ck_4e34eda4882e044742e643e35060ab5234231ab3"
private const val SECRET = "cs_28f528089254ed3dc6bc8cbd0ffeb34a52f69547"
interface ApiService {

    @GET("products")
    suspend fun getLastProducts(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("perpage") perPage: Int = 100
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
        @Query("perpage") perPage: Int = 100

    ): List<Product>

    @GET("products")
    suspend fun getFavoriteProducts(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("orderby") orderby : String ="popularity",
        @Query("perpage") perPage: Int = 100

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

}