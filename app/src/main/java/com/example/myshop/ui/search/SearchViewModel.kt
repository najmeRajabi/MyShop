package com.example.myshop.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Attribute
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    val attributes = MutableLiveData<List<Attribute>>()

    var searchKey = ""
    var sortItem = "date"
    var filter = ""

    init {
        retrieveAllProductAttribute()
    }


    fun searchInProducts() {
        viewModelScope.launch {
            try {
                searchList.postValue(productRepository.searchInProducts(searchKey , sortItem,filter))
            }catch (e: Exception){
                Log.d("searchVM---TAG", "searchInProducts: ${e.message}")

            }
        }
    }


    fun retrieveAllProductAttribute() {
        viewModelScope.launch {
            try {
                attributes.postValue(productRepository.retrieveAllProductAttribute())
            }catch (e: Exception){
                Log.d("search----TAG", "retrieveAllProductAttribute: ${e.message}")
            }
        }
    }
}