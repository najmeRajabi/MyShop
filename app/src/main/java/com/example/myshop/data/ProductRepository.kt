package com.example.myshop.data

import com.example.myshop.model.Category
import com.example.myshop.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    suspend fun getLastProducts(): List<Product>{
        return productRemoteDataSource.getLastProducts()
    }

    suspend fun getMostSeenProducts(): List<Product>{
        return productRemoteDataSource.getMostSeenProducts()
    }

    suspend fun getFavoriteProducts(): List<Product>{
        return productRemoteDataSource.getFavoriteProducts()
    }

    suspend fun getCategories(): List<Category>{
        return productRemoteDataSource.getCategories()
    }
}