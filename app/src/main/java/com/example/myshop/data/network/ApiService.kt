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
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("per_page") perPage: Int = 100
    ): Response<List<Product>>

    @GET("products/{id}")
    suspend fun getProductById(
        @Path("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): Response<Product>

    @GET("products/categories")
    suspend fun getCategories(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET
    ): Response<List<Category>>

    @GET("products/")
    suspend fun getProductsByCategory(
        @Query("category") categoryId: String,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
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
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("per_page") perPage: Int = 100
    ): Response<List<Product>>

    // order ....................................................

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
        @Body status: String = "completed",
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):Response<List<Order>>

    @GET("orders/")
    suspend fun retrieveOrder(
        @Query("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):Response<List<Order>>

    @DELETE("orders/")
    suspend fun deleteOrder(
        @Query("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Query("force") force: Boolean = true
    ):Response<List<Order>>

    //.... customer...................................................

    @POST("customers")
    suspend fun register(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
        @Body customer: Customer
    ): Response<List<Customer>>

    @GET("customers/{id}")
    suspend fun login(
        @Path("id") id: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ): Response<Customer>

    //...... review.................................................

    @GET("products/reviews/")
    suspend fun retrieveReview(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):Response<List<Review>>

    @POST("products/reviews/")
    suspend fun createReview(
        @Body review: Review,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET,
    ):Response<Review>


    @GET("products/attributes")
    suspend fun retrieveAllProductAttribute(
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET
    ): Response<List<Attribute>>

    @GET("products/attributes/{id}/terms")
    suspend fun retrieveAttributeTerm(
        @Path("id") attributeId: Int,
        @Query("consumer_key") key : String = KEY,
        @Query("consumer_secret") secret : String = SECRET
    ): Response<List<Terms>>


    ////////////////////////////////////////////////////////////////////////////////////////////

//    @GET("products")
//    suspend fun getProducts(
//        @Query("search") searchText: String?,
//        @Query("attribute") attribute: String?,
//        @Query("attribute_term") terms: String?,
//        @Query("per_page") perPage: Int,
//        @Query("page") numberOfPage: Int,
//        @Query("orderby") baseOn: String?,
//        @Query("order") order: String?
//    ): Response<List<Product>>

//    @GET("products")
//    fun getProducts(
//        @Query("search") searchText: String?,
//        @Query("attribute") attribute: String?,
//        @Query("attribute_term") terms: String?,
//        @Query("per_page") perPage: Int,
//        @Query("page") numberOfPage: Int
//    ): Call<List<Product?>?>?


//    @GET("products")
//    fun getProducts(
//        @Query("attribute") attribute: String?,
//        @Query("attribute_term") terms: String?,
//        @Query("per_page") perPage: Int,
//        @Query("page") numberOfPage: Int
//    ): Call<List<Product?>?>?


//    @GET("products")
//    fun getProducts(
//        @Query("per_page") perPage: Int,
//        @Query("page") numberOfPage: Int,
//        @Query("orderby") baseOn: String?
//    ): Call<List<Product?>?>?

    ///// With Category

    ///// With Category
    @GET( "products")
    suspend fun getProducts(
        @Query("category") category: String? = null,
        @Query("search") searchText: String? = null,
        @Query("attribute") attribute: String? = null,
        @Query("attribute_term") terms: String? = null,
        @Query("per_page") perPage: Int = 100,
        @Query("page") numberOfPage: Int,
        @Query("orderby") baseOn: String? = "date",
        @Query("order") order: String? = null
    ): Response<List<Product>>

//    @GET("products")
//    fun getProducts(
//        @Query("category") category: String?,
//        @Query("search") searchText: String?,
//        @Query("attribute") attribute: String?,
//        @Query("attribute_term") terms: String?,
//        @Query("per_page") perPage: Int,
//        @Query("page") numberOfPage: Int
//    ): Call<List<Product?>?>?

}