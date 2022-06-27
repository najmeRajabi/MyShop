package com.example.myshop.data

import com.example.myshop.model.*
import com.example.myshop.model.Attribute
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {


    suspend fun getSortedProducts(orderBy: String): Resource<List<Product>> {
        return productRemoteDataSource.getSortedProducts(orderBy)
    }

    suspend fun getProductById(id: Int): Resource<Product> {
        return productRemoteDataSource.getProductById(id)
    }

    suspend fun getCategories(): Resource<List<Category>> {
        return productRemoteDataSource.getCategories()
    }

    suspend fun getProductsByCategory(categoryId: String): Resource<List<Product>> {
        return productRemoteDataSource.getProductsByCategory(categoryId)
    }

    suspend fun searchInProducts(searchKey: String, sortItem: String , attribute: String): List<Product> {
        return productRemoteDataSource.searchInProducts(searchKey,sortItem, attribute)
    }

    suspend fun createOrder(order: Order): Resource<Order> {
        return productRemoteDataSource.createOrder(order)
    }

    suspend fun updateOrder(order: Order,id: Int): Resource<Order> {
        return productRemoteDataSource.updateOrder(id,order)
    }

    suspend fun retrieveOrder(id: Int): Resource<List<Order>> {
        return productRemoteDataSource.retrieveOrder(id)
    }

    suspend fun register(customer: Customer): Resource<List<Customer>> {
        return productRemoteDataSource.register(customer = customer)
    }

    suspend fun login(id: Int): Resource<List<Customer>> {
        return productRemoteDataSource.login(id)
    }

    suspend fun retrieveReview(): Resource<List<Review>> {
        return productRemoteDataSource.retrieveReview()

    suspend fun retrieveAllProductAttribute(): List<Attribute> {
        return productRemoteDataSource.retrieveAllProductAttribute()
    }
}
data class Resource<T>(var status: State, var data: T?, var message: String? = null)