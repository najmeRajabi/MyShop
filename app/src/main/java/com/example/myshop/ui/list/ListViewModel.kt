package com.example.myshop.ui.list

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
class ListViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel(){

    val productList = MutableLiveData<List<Product>>()

    fun getProductList(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productList.postValue( productRepository.getProductList(id))
            }catch (e: Exception){
                Log.d("ListViewModel----tag", "getProducts: $e")
            }
        }
    }
}