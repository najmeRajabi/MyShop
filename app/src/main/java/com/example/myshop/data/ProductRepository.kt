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

    suspend fun getProduct(id: Int): Product {
        return productRemoteDataSource.getProduct(id)
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

    suspend fun getProductList(id: Int): List<Product> {
        return productRemoteDataSource.getProductList(id)
    }
}