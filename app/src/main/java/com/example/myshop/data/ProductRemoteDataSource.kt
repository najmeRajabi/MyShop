package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.*
import com.example.myshop.ui.disconnect.State
import com.example.myshop.ui.handleRequestCode
import retrofit2.Response
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getLastProducts(orderby: String): Resource<List<Product>>{
     //   return apiService.getLastProducts()
       return try {
            val response = apiService.getLastProducts(orderby)
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

    suspend fun getProductById(id: Int): Resource<Product> {
        return try {
            val response = apiService.getProductById(id)
            val message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, null,message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED, null,e.message)
        }
    }


    suspend fun getCategories(): Resource<List<Category>>{
        return try {
            val response = apiService.getCategories()
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

    suspend fun getProductList(id: Int): Resource<List<Product>> {
        return try {
            val response = apiService.getProductList(id)
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

    suspend fun getProductsByCategory(categoryId: String): Resource<List<Product>> {
//        return apiService.getProductsByCategory(categoryId = categoryId)
        return try {
            val response = apiService.getProductsByCategory(categoryId)
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

    suspend fun retrieveOrder(id: Int): Resource<List<Order>> {

        return try {
            val response = apiService.retrieveOrder(id)
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