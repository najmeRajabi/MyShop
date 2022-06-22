package com.example.myshop.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Order
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import java.lang.Exception
import javax.inject.Inject

const val ORDER= "order"
const val ORDER_ID= "orderId"
const val ORDER_OBJECT= "orderObject"

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
): BaseViewModel() {

    val product = MutableLiveData<Product>()
    val orderMessage= MutableLiveData<String>()
    val orderCallback= MutableLiveData<Order>()


    fun getProduct(id: Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                product.postValue( productRepository.getProductById(id))
            }catch (e: Exception){
                Log.d("ProductViewModel----tag", "getProduct: $e")
            }
        }
    }

    fun createOrder() {
        Log.d("TAG", "createOrder: ${product.value}")
        val order = Order(0, listOf(product.value!!))
        Log.d("TAG", "createOrder: $order")
        viewModelScope.launch {
            try {
                orderCallback.postValue(productRepository.createOrder(order))
                orderMessage.postValue("به لیست خرید افزوده شد.")
            }catch (e: Exception){
                orderMessage.postValue(e.message)
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun saveOrderToSharedPreferences(context: Context) {
        if (orderCallback.value != null){
            val sharedPreferences = context.getSharedPreferences(ORDER,Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(ORDER_ID, orderCallback.value!!.id)
//            editor.putString(ORDER_OBJECT, orderCallback.value!!.line_items[0].name)
            editor.apply()
        }
    }
}