package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getLastProducts(): List<Product>{
        return apiService.getLastProducts()
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


}