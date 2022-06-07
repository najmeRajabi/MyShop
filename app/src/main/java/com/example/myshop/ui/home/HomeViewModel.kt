package com.example.myshop.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val productRepository: ProductRepository
    ): ViewModel() {

    val products = MutableLiveData<List<Product>>()

    init {
        getProducts()
    }

        fun getProducts(){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    products.value = productRepository.getProducts()
                }catch (e: Exception){
                    Log.d("HomeViewModel----tag", "getProducts: $e")
                }
            }
        }
}