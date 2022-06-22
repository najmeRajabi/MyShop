package com.example.myshop.data

import com.example.myshop.model.Attribute
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    suspend fun getLastProducts(): Resource<List<Product>> {
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

    suspend fun getProductsByCategory(categoryId: String): List<Product> {
        return productRemoteDataSource.getProductsByCategory(categoryId)
    }

    suspend fun searchInProducts(searchKey: String, sortItem: String , attribute: String): List<Product> {
        return productRemoteDataSource.searchInProducts(searchKey,sortItem, attribute)
    }


    suspend fun retrieveAllProductAttribute(): List<Attribute> {
        return productRemoteDataSource.retrieveAllProductAttribute()
    }
}
data class Resource<T>(var status: State, var data: T?, var message: String? = null)