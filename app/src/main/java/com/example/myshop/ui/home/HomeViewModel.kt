package com.example.myshop.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
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
    val state = MutableLiveData<State>()
    val serverError = MutableLiveData<Boolean>()
    var splashFlag = true

    init {
        getLastProducts()
        getMostSeenProducts()
        getFavoriteProducts()
    }

        fun getLastProducts(){
            viewModelScope.launch(Dispatchers.IO) {
                state.postValue(State.LOADING)
                try {
                    delay(2000)
                    recentProducts.postValue( productRepository.getLastProducts())
                    state.postValue(State.SUCCESS)
                }catch (e: Exception){
                    state.postValue(State.FAILED)
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
