package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.*
import com.example.myshop.ui.disconnect.State
import com.example.myshop.ui.handleRequestCode
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getLastProducts(): Resource<List<Product>>{
     //   return apiService.getLastProducts()
       return try {
            val response = apiService.getLastProducts()
            val message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, arrayListOf(),message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED, arrayListOf(),e.message)
        }
    }

    suspend fun getProductById(id: Int): Product {
        return apiService.getProductById(id)
    }

    suspend fun getMostSeenProducts(): List<Product>{
        return apiService.getMostSeenProducts()
    }

    suspend fun getFavoriteProducts(): List<Product>{
        return apiService.getFavoriteProducts()
    }

    suspend fun getCategories(): List<Category>{
        return apiService.getCategories()
    }

    suspend fun getProductList(id: Int): List<Product> {
        return apiService.getProductList(id)
    }

    suspend fun getProductsByCategory(categoryId: String): List<Product> {
        return apiService.getProductsByCategory(categoryId = categoryId)
    }

    suspend fun searchInProducts(searchKey: String): List<Product> {
        return apiService.searchInProducts(searchKey = searchKey)
    }

    suspend fun sortProducts(sortItem: String): List<Product> {
        return apiService.sortProducts(sortItem)
    }

    suspend fun createOrder(order: Order): Resource<Order> {
        return try {
            val response = apiService.createOrder(order)
            val message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, Order(0, arrayListOf()),message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED, Order(0, arrayListOf()),e.message)
        }
    }

    suspend fun updateOrder (id: Int,order: Order): Resource<Order> {
        return try {
            val response = apiService.updateOrder(order,id)
            val message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, Order(0, arrayListOf()),message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED, Order(0, arrayListOf()),e.message)
        }
    }

    suspend fun retrieveOrder(id: Int): List<Order> {
        return apiService.retrieveOrder(id)
    }

    suspend fun register(customer: Customer): List<Customer> {
        return apiService.register(customer = customer)
    }

    suspend fun login(id: Int): Customer {
        return apiService.login(id)
    }

    suspend fun retrieveReview(): List<Review> {
        return apiService.retrieveReview()
    }

}