package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.*
import com.example.myshop.model.Attribute
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
import com.example.myshop.ui.handleRequestCode
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getSortedProducts(orderby: String): Resource<List<Product>>{
     //   return apiService.getLastProducts()
       return try {
            val response = apiService.getSortedProducts(orderby)
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

    suspend fun getProductsByCategory(categoryId: String): Resource<List<Product>> {
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

    suspend fun searchInProducts(searchKey: String, sortItem: String , attribute: String): List<Product> {
        return apiService.searchInProducts(searchKey = searchKey,sortItem,attribute)
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

    suspend fun register(customer: Customer): Resource<List<Customer>> {
        return try {
            val response = apiService.register(customer = customer)
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

    suspend fun login(id: Int): Resource<List<Customer>> {
        return try {
            val response = apiService.login(id)
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

    suspend fun retrieveReview(): Resource<List<Review>> {
        return try {
            val response = apiService.retrieveReview()
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

    suspend fun retrieveAllProductAttribute(): List<Attribute> {
        return apiService.retrieveAllProductAttribute()
    }

}