package com.example.myshop.data

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myshop.model.Category
import com.example.myshop.model.Order
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    val shoppingList = MutableLiveData<Order>()

    suspend fun getLastProducts(): Resource<List<Product>> {
        return productRemoteDataSource.getLastProducts()
    }

    suspend fun getProductById(id: Int): Product {
        return productRemoteDataSource.getProductById(id)
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

    suspend fun searchInProducts(searchKey: String): List<Product> {
        return productRemoteDataSource.searchInProducts(searchKey)
    }

    suspend fun sortProducts(sortItem: String): List<Product> {
        return productRemoteDataSource.sortProducts(sortItem)
    }

    suspend fun createOrder(order: Order): Order? {
        shoppingList.postValue(productRemoteDataSource.createOrder(order)[1])
        return shoppingList.value
    }

    suspend fun retrieveOrder(id: Int):Order{
        return productRemoteDataSource.retrieveOrder(id)[0]
    }
}
data class Resource<T>(var status: State, var data: T?, var message: String? = null)