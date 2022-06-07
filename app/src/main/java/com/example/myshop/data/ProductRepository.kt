package com.example.myshop.data

import com.example.myshop.model.Product
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    suspend fun getProducts(): List<Product>{
        return productRemoteDataSource.getProducts()
    }
}