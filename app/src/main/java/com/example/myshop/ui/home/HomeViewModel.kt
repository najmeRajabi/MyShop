package com.example.myshop.ui.home

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
class HomeViewModel @Inject constructor(
    val productRepository: ProductRepository
    ): BaseViewModel() {

    val recentProducts = MutableLiveData<List<Product>>()
    val mostSeenProducts = MutableLiveData<List<Product>>()
    val favoriteProducts = MutableLiveData<List<Product>>()
    val serverError = MutableLiveData<Boolean>()

    init {
        getLastProducts()
        getMostSeenProducts()
        getFavoriteProducts()
    }

        fun getLastProducts(){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    recentProducts.postValue( productRepository.getLastProducts())
                }catch (e: Exception){
                    serverError.postValue(false)
                    Log.d("HomeViewModel----tag", "getProducts: $e")
                }
            }
        }

    fun getMostSeenProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                mostSeenProducts.postValue( productRepository.getMostSeenProducts())
            }catch (e: Exception){
                serverError.postValue(false)
                Log.d("HomeViewModel----tag", "getMostSeenProducts: $e")
            }
        }
    }

    fun getFavoriteProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                favoriteProducts.postValue( productRepository.getFavoriteProducts())
            }catch (e: Exception){
                serverError.postValue(false)
                Log.d("HomeViewModel----tag", "getFavProducts: $e")
            }
        }
    }


}
