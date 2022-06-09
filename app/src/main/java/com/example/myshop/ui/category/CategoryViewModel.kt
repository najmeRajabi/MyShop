package com.example.myshop.ui.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Category
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel@Inject constructor(
    val productRepository: ProductRepository
    ):BaseViewModel() {

    val categories = MutableLiveData<List<Category>>()

    init {
        getCategories()
    }

    fun getCategories(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                categories.postValue( productRepository.getCategories())
            }catch (e: Exception){
                Log.d("CategoryViewModel---tag", "getCategories: $e")
            }
        }
    }
}