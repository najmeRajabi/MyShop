package com.example.myshop.ui.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val productRepository: ProductRepository
): BaseViewModel() {

    val searchList = MutableLiveData<List<Product>>()

    fun searchInProducts(searchKey: String) {
        viewModelScope.launch {
            try {
                searchList.postValue(productRepository.searchInProducts(searchKey))
            }catch (e: Exception){
                Log.d("searchVM---TAG", "searchInProducts: $e")

            }
        }
    }
}