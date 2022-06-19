package com.example.myshop.ui.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
): BaseViewModel() {

    val product = MutableLiveData<Product>()


    fun getProduct(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                product.postValue( productRepository.getProductById(id))
            }catch (e: Exception){
                Log.d("ProductViewModel----tag", "getProduct: $e")
            }
        }
    }

    fun addToCart() {
        productRepository.addToCart(product)
    }
}