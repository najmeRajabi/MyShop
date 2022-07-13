package com.example.myshop.data.network

import com.example.myshop.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


    interface ApiService {





    @GET("products")
    suspend fun getSortedProducts(
        @Query("orderby") orderby : String ,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Query("per_page") perPage: Int = 100,
    ): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ): Response<Product>

    @GET("products/categories")
    suspend fun getCategories(
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ): Response<List<Category>>

    @GET("products/")
    suspend fun getProductsByCategory(
        @Query("category") categoryId: String,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 100
    ): Response<List<Product>>

    @GET("products/")
    suspend fun searchInProducts(
        @Query("category") category: String? = null,
        @Query("search") searchKey : String? = null,
        @Query("orderby") orderby : String? = "date",
        @Query("attribute") attribute : String?  = null,
        @Query("attribute_term") terms: Int? = null,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Query("per_page") perPage: Int = 100
    ): Response<List<Product>>

    // order ....................................................

    @Headers("Content-Type: application/json")
    @POST("orders/")
    suspend fun createOrder(
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Body order: Order,
    ):Response<Order>

    @PUT("orders/{id}")
    suspend fun updateOrder(
        @Body order: Order,
        @Path("id") id: Int,
        @Query ("status")status: String = "completed",
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ):Response<Order>

    @GET("orders/{id}")
    suspend fun retrieveOrder(
        @Path("id") id: Int,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ):Response<OrderCallback>

    @DELETE("orders/{id}")
    suspend fun deleteOrder(
        @Path("id") id: Int,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Query("force") force: Boolean = true
    ):Response<Order>

    //.... customer...................................................

    @POST("customers")
    suspend fun register(
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Body customer: Customer
    ): Response<List<Customer>>

    @GET("customers/{id}")
    suspend fun login(
        @Path("id") id: Int,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ): Response<Customer>

    //...... review.................................................

    @GET("products/reviews/")
    suspend fun retrieveReview(
        @Query("product") productId: List<Int>,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Query("per_page") perPage: Int = 100
    ):Response<List<Review>>

    @POST("products/reviews/")
    suspend fun createReview(
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
        @Body review: Review,
        ):Response<Review>



    @DELETE("products/reviews/{id}")
    suspend fun deleteReview(
        @Path ("id") id: Int,
        @QueryMap options: Map<String, String> = NetworkParams.getBaseOptions(),
        @Query("force") force: Boolean = true
    ): Response<Review>

    @PUT("products/reviews/{id}")
    suspend fun updateReview(
        @Path ("id") id: Int,
        @QueryMap options: Map<String, String> = NetworkParams.getBaseOptions(),
        @Body review: Review,
        ): Response<Review>


    @GET("products/attributes")
    suspend fun retrieveAllProductAttribute(
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ): Response<List<Attribute>>

    @GET("products/attributes/{id}/terms")
    suspend fun retrieveAttributeTerm(
        @Path("id") attributeId: Int,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ): Response<List<Terms>>


}