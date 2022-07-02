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
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val shoppingList = MutableLiveData<List<Product>>()
    val order = MutableLiveData<Order>()
    val state = MutableLiveData<State>(State.FAILED)
    val price = MutableLiveData<String>()
    var count = MutableLiveData<List<Int>>()
    var orderId =-1

    init {
        getShoppingList()
    }

    fun calculatePrice() {
        var counter = 0L
        if (!shoppingList.value.isNullOrEmpty() && !count.value.isNullOrEmpty()) {
            for (index in 0 until shoppingList.value!!.size) {
                counter += (shoppingList.value!![index].price.toLong()).times(count.value!![index])
            }
            price.value = "%,d".format(counter) + " تومان"
            Log.d("cart---TAG", "calculatePrice: ${"%,d".format(counter) + " تومان"}")
        }
        if (counter == 0L){
            state.postValue(State.FAILED)
        }else
            state.postValue(State.SUCCESS)
    }

    fun getShoppingList() {
//        val fakeList = arrayListOf(
//            Product(0, arrayListOf(),"n1","200", arrayListOf(),1.0f,"de1",2,
//                arrayListOf(),),
//            Product(1, arrayListOf(),"n2","10", arrayListOf(),1.0f,"de2",2,
//                arrayListOf(),),
//            Product(2, arrayListOf(),"n1","100", arrayListOf(),1.0f,"de3",2,
//                arrayListOf(),)
//        )
//        shoppingList.postValue(fakeList)
//        state.postValue(State.SUCCESS)
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            try {
                shoppingList.postValue( productRepository.retrieveOrder(orderId).data!![0].line_items)
            }catch (e: Exception){
                state.postValue(State.FAILED)
            }
            state.postValue(productRepository.retrieveOrder(orderId).status)
            message.postValue(productRepository.retrieveOrder(orderId).message)
        }

    }

    fun getOrderFromSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        orderId= sharedPreferences.getInt(ORDER_ID, -1)

    }

    fun deleteOrderFromSharedPreferences(context: Context) {
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(ORDER_ID).apply()

    }

    fun removeProduct(product: Product , context: Context) {
        var productList = shoppingList.value?.minus(product)
        val mOrder = productList?.let { Order(orderId , it) }
        if (mOrder != null) {
            shoppingList.postValue(mOrder.line_items)
        }

        viewModelScope.launch {
            try {
                state.postValue(State.LOADING)
                if (mOrder?.line_items.isNullOrEmpty()){
                    deleteOrderFromSharedPreferences(context )
                    shoppingList.postValue(mOrder?.let {
                        productRepository.deleteOrder(
                            orderId
                        ).data!![0]
                    }?.line_items)
                    state.postValue(mOrder?.let {
                        productRepository.deleteOrder(
                            orderId
                        ).status
                    })
                    message.postValue(mOrder?.let {
                        productRepository.deleteOrder(
                            orderId
                        ).message
                    })
                }else {
                    shoppingList.postValue(mOrder?.let {
                        productRepository.updateOrder(
                            it,
                            orderId
                        ).data!![0]
                    }?.line_items)
                    state.postValue(mOrder?.let {
                        productRepository.updateOrder(
                            it,
                            orderId
                        ).status
                    })
                    message.postValue(mOrder?.let {
                        productRepository.updateOrder(
                            it,
                            orderId
                        ).message
                    })
                }
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(mOrder?.let {
                    productRepository.updateOrder(it,orderId).message + e.message })

            }
        }
    }
}

