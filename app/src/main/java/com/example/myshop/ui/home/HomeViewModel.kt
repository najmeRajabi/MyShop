package com.example.myshop.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SPECIAL_OFFERS = 608

@HiltViewModel
class HomeViewModel @Inject constructor(
    val productRepository: ProductRepository
    ): BaseViewModel() {

    val recentProducts = MutableLiveData<List<Product>>()
    val mostSeenProducts = MutableLiveData<List<Product>>()
    val favoriteProducts = MutableLiveData<List<Product>>()
    val specialProduct = MutableLiveData<Product>()
    val state = MutableLiveData<State>()
    var splashFlag = true

    init {
        getLastProducts()
        getMostSeenProducts()
        getFavoriteProducts()
    }


    fun getProductByOrder(orderBy: String , list: MutableLiveData<List<Product>>){
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            list.postValue( productRepository.getSortedProducts(orderBy).data!!)
            state.postValue(productRepository.getSortedProducts(orderBy).status)
            message.postValue(productRepository.getSortedProducts(orderBy).message)
        }
    }

    fun getLastProducts(){
        getProductByOrder("date",recentProducts)
    }

    fun getMostSeenProducts(){
        getProductByOrder("rating",mostSeenProducts)
    }

    fun getFavoriteProducts(){
        getProductByOrder("popularity",favoriteProducts)
    }

    fun getSpecialOffers(){
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            if (productRepository.getProductById(SPECIAL_OFFERS).data == null){
                state.postValue(State.FAILED)
            }else{
                specialProduct.postValue (productRepository.getProductById(SPECIAL_OFFERS).data!!)
            }
            state.postValue(productRepository.getProductById(SPECIAL_OFFERS).status)
            message.postValue(productRepository.getProductById(SPECIAL_OFFERS).message)
        }

    }


}
