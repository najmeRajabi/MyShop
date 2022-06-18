package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
import com.example.myshop.ui.handleRequestCode
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getLastProducts(): Resource<List<Product>>{
     //   return apiService.getLastProducts()
       return try {
            var response = apiService.getLastProducts()
            var message=handleRequestCode(response.code())
            if (response.isSuccessful){
                Resource(State.SUCCESS,response.body(),message)
            }else{
                Resource(State.FAILED, arrayListOf(),message)
            }

        } catch (e: Exception) {
            Resource(State.FAILED, arrayListOf(),e.message)
        }
    }

    suspend fun getProduct(id: Int): Product {
        return apiService.getProduct(id)
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


}