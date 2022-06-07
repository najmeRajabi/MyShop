package com.example.myshop.data

import com.example.myshop.data.network.ApiService
import com.example.myshop.model.Product
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(val apiService: ApiService) {

    suspend fun getProducts(): List<Product>{
        return apiService.getProducts()
    }


}