package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.Product
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getLastProducts(): List<Product>{
        return apiService.getLastProducts()
    }

    suspend fun getMostSeenProducts(): List<Product>{
        return apiService.getMostSeenProducts()
    }

    suspend fun getFavoriteProducts(): List<Product>{
        return apiService.getFavoriteProducts()
    }


}