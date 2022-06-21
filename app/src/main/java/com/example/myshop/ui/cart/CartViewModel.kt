package com.example.myshop.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val productRepository: ProductRepository
): ViewModel() {

    val shoppingList = MutableLiveData<List<Product>>()
    init {
        getShoppingList()
    }

    private fun getShoppingList(){
        shoppingList.postValue(productRepository.shoppingList)
    }
}