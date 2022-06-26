package com.example.myshop.ui.cart

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Order
import com.example.myshop.model.Product
import com.example.myshop.ui.detail.ORDER
import com.example.myshop.ui.detail.ORDER_ID
import com.example.myshop.ui.disconnect.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val shoppingList = MutableLiveData<List<Product>>()
    val order = MutableLiveData<Order>()
    val price = MutableLiveData<String>()
    var count = MutableLiveData<Int>(1)
    var orderId =-1

    init {
        getShoppingList()
    }

    fun calculatePrice() {
        var counter = 0L
        if (shoppingList.value != null) {
            for (mProduct in shoppingList.value!!) {
                counter += (mProduct.price.toLong())* count.value!!
            }
            price.value = "%,d".format(counter) + " تومان"
            Log.d("cart---TAG", "calculatePrice: ${"%,d".format(counter) + " تومان"}")
        }
    }

    fun getShoppingList() {
        viewModelScope.launch {
            try {
                if (orderId != -1) {
                    shoppingList.postValue(productRepository.retrieveOrder(orderId)[0].line_items)
                    order.postValue(productRepository.retrieveOrder(orderId)[0])
                }

                Log.d("cart-----TAG", "getShoppingList  true: ${productRepository.retrieveOrder(orderId)[0].line_items[0]}")
            }catch (e: Exception){

//                Log.d("cart-----TAG", "getShoppingList: ${productRepository.retrieveOrder(orderId).line_items[0]}")
            }
        }

    }

    fun getOrderFromSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        orderId= sharedPreferences.getInt(ORDER_ID, -1)

    }

    fun removeProduct(product: Product ) {
        var productList = shoppingList.value?.minus(product)
        val mOrder = productList?.let { Order(orderId , it) }

        viewModelScope.launch {
            try {
                shoppingList.postValue(mOrder?.let { productRepository.updateOrder(it,orderId).data }!!.line_items)
            }catch (e: Exception){

            }
        }
    }
}

