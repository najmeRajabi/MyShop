package com.example.myshop.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Attribute
import com.example.myshop.model.Product
import com.example.myshop.model.Terms
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*
import javax.inject.Inject

enum class SortItemProduct{
    DATE , INCLUDE , TITLE , SLUG , ID , PRICE , POPULARITY , RATING , DESC
}

@HiltViewModel
class SearchViewModel @Inject constructor(
    val productRepository: ProductRepository
): BaseViewModel() {

    val searchList = MutableLiveData<List<Product>>()
    val sortedList = MutableLiveData<List<Product>>()
    val state = MutableLiveData<State>()
    val attributes = MutableLiveData<List<Attribute>>()
    val terms = MutableLiveData<List<Terms>>()
    val sortedItems = listOf("پربازدید ترین","محبوبترین","جدیدترین","گران ترین","ارزانترین")

    var category : String? = null
    var searchKey : String? = null
    var sortItem : String? = null
    var filter : String? = null
    var term : Int? = null

    init {
        retrieveAllProductAttribute()

    }


    fun searchInProducts() {
        viewModelScope.launch {
            state.postValue(State.LOADING)
            try {
                searchList.postValue(productRepository.searchInProducts(category,searchKey , sortItem,filter,term).data!!)
                state.postValue(productRepository.searchInProducts(category,searchKey,sortItem,filter,term).status)
                message.postValue(productRepository.searchInProducts(category,searchKey,sortItem,filter,term).message)
            }catch (e: Exception){
                Log.d("searchVM---TAG", "searchInProducts: ${e.message}")
                state.postValue(State.FAILED)
                message.postValue(productRepository.searchInProducts(category,searchKey,sortItem,filter,term).message + e.message)
            }
        }
    }


    fun retrieveAllProductAttribute() {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            try {
                Log.d(
                    "attri----TAG",
                    "retrieveAllProductAttribute: ${productRepository.retrieveAllProductAttribute().data}")
                attributes.postValue(productRepository.retrieveAllProductAttribute().data!!)
                state.postValue(productRepository.retrieveAllProductAttribute().status)
                message.postValue(productRepository.retrieveAllProductAttribute().message)
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(productRepository.retrieveAllProductAttribute().message + e.message)
            }

        }
    }

    fun retrieveTerms(attribute: Attribute){
        viewModelScope.launch {
            state.postValue(State.LOADING)
            try {
                terms.postValue(productRepository.retrieveAttributeTerm(attribute.id).data!!)
                state.postValue(productRepository.retrieveAttributeTerm(attribute.id).status)
                message.postValue(productRepository.retrieveAttributeTerm(attribute.id).message)
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(productRepository.retrieveAttributeTerm(attribute.id).message + e.message)
            }
        }
    }
}