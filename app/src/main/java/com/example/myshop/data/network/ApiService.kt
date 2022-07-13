package com.example.myshop.data.network

import com.example.myshop.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


//private const val KEY = "ck_4e34eda4882e044742e643e35060ab5234231ab3"
private const val KEY = "ck_63f4c52da932ddad1570283b31f3c96c4bd9fd6f"
//private const val SECRET = "cs_28f528089254ed3dc6bc8cbd0ffeb34a52f69547"
private const val SECRET = "cs_294e7de35430398f323b43c21dd1b29f67b5370b"

//class NetWorkParams () {
//
//    fun getBaseOptions() {
//        val queryMap: HashMap<String, String> = HashMap()
//        queryMap[KEY] = query1
//        queryMap[SECRET] = query2
//    }
//}
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
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ):Response<List<Review>>

    @POST("products/reviews/")
    suspend fun createReview(
        @Body review: Review,
        @QueryMap options: Map<String , String> = NetworkParams.getBaseOptions(),
    ):Response<List<Review>>



    @DELETE("products/reviews/{id}")
    suspend fun deleteReview(
        @Path ("id") id: Int,
        @QueryMap options: Map<String, String> = NetworkParams.getBaseOptions()
    ): Response<Review>

    @PUT("products/reviews/{id}")
    suspend fun updateReview(
        @Path ("id") id: Int,
        @Body review: Review,
        @QueryMap options: Map<String, String> = NetworkParams.getBaseOptions()
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