package com.example.myshop.ui.list

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
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel(){

    val productList = MutableLiveData<List<Product>>()
    val state = MutableLiveData<State>()

    fun getProductList(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            productList.postValue( productRepository.getProductsByCategory(id.toString()).data!!)
            state.postValue(productRepository.getProductsByCategory(id.toString()).status)
            message.postValue(productRepository.getProductsByCategory(id.toString()).message)}
    }
}