package com.example.myshop.ui.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel@Inject constructor(
    val productRepository: ProductRepository
    ): BaseViewModel() {

    val categories = MutableLiveData<List<Category>>()
    val state = MutableLiveData<State>()

    init {
        getCategories()
    }

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            categories.postValue( productRepository.getCategories().data!!)
            state.postValue(productRepository.getCategories().status)
            message.postValue(productRepository.getCategories().message)}
    }
}