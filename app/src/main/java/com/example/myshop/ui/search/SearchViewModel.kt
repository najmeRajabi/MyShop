package com.example.myshop.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

enum class SortItemProduct{
    DATE , INCLUDE , TITLE , SLUG , ID , PRICE , POPULARITY , RATING
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    val productRepository: ProductRepository
): BaseViewModel() {

    val searchList = MutableLiveData<List<Product>>()
    val sortedList = MutableLiveData<List<Product>>()
    val state = MutableLiveData<State>()


    fun searchInProducts(searchKey: String) {
        viewModelScope.launch {
            try {
                searchList.postValue(productRepository.searchInProducts(searchKey))
                Log.d("searchVM---TAG", "searchInProducts: ${productRepository.searchInProducts(searchKey)[1]}")

            }catch (e: Exception){
                Log.d("searchVM---TAG", "searchInProducts: ${e.message}")

            }
        }
    }

    fun sortProduct(orderBy: String){
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            searchList.postValue( productRepository.getSortedProducts(orderBy).data!!)
            state.postValue(productRepository.getSortedProducts(orderBy).status)
            message.postValue(productRepository.getSortedProducts(orderBy).message)
        }
    }
}