package com.example.myshop.data

import androidx.lifecycle.MutableLiveData
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
import javax.inject.Inject

class ProductRepository @Inject constructor(
    val productRemoteDataSource: ProductRemoteDataSource
) {

    val shoppingList = arrayListOf<Product>()

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

    fun addToCart(product: MutableLiveData<Product>) {
        product.value?.let { shoppingList.add(it) }
    }
}
data class Resource<T>(var status: State, var data: T?, var message: String? = null)