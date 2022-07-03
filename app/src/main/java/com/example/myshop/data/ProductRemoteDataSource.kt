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

    suspend fun getSortedProducts(orderby: String ): Resource<List<Product>>{
       return try {
            val response = apiService.getSortedProducts(orderby )
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

    suspend fun searchInProducts(
        category: String?,searchKey: String?, sortItem: String? , attribute: String? , terms: Int?
    ): Resource<List<Product>?> {
        return try {
            val response = apiService.searchInProducts(category,searchKey,sortItem,attribute , terms)
            val message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, arrayListOf(),message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED,  arrayListOf(),e.message)
        }
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

    suspend fun updateOrder (id: Int,order: Order): Resource<List<Order>> {
        return try {
            val response = apiService.updateOrder(order,id)
            val message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, arrayListOf(),message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED,  arrayListOf(),e.message)
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

    suspend fun deleteOrder(id: Int): Resource<List<Order>> {

        return try {
            val response = apiService.deleteOrder(id)
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

    suspend fun retrieveAllProductAttribute(): Resource<List<Attribute>> {
        return try {
            val response = apiService.retrieveAllProductAttribute()
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

    suspend fun retrieveAttributeTerm(id: Int): Resource<List<Terms>> {
        return try {
            val response = apiService.retrieveAttributeTerm(id)
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


}