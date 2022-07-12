package com.example.myshop.ui.cart

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.LineItems
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
    val lineItemList = MutableLiveData<List<LineItems>>()
    val order = MutableLiveData<Order>()
    val state = MutableLiveData<State>(State.FAILED)
    val price = MutableLiveData<String>()
    var count = MutableLiveData<List<Int>>()
    var discount: String? = null
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
                lineItemList.postValue( productRepository.retrieveOrder(orderId).data!![0].line_items)
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

    fun updateOrder(order: Order?){
        viewModelScope.launch {
            try {
                lineItemList.postValue(order?.let {
                    productRepository.updateOrder(
                        it, orderId).data!![0]
                }?.line_items)
                state.postValue(order?.let {
                    productRepository.updateOrder(
                        it, orderId).status
                })
                message.postValue(order?.let {
                    productRepository.updateOrder(
                        it, orderId).message
                })
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(order?.let {
                    productRepository.updateOrder(
                        it, orderId).message + e.message
                })
            }
        }
    }
    fun deleteOrder(order: Order?){
        viewModelScope.launch {
            try {
                lineItemList.postValue(order?.let {
                    productRepository.deleteOrder(
                        orderId).data!![0]
                }?.line_items)
                state.postValue(order?.let {
                    productRepository.deleteOrder(
                        orderId).status
                })
                message.postValue(order?.let {
                    productRepository.deleteOrder(
                        orderId).message
                })
            }catch (e: Exception){

            }
        }
    }

    fun removeProduct(lineItem: LineItems, context: Context) {
        var productList = lineItemList.value?.minus(lineItem)
        val mOrder = productList?.let { Order(orderId , it) }
        if (mOrder != null) {
            lineItemList.postValue(mOrder.line_items)
        }

        viewModelScope.launch {
            try {
                state.postValue(State.LOADING)
                if (mOrder?.line_items.isNullOrEmpty()){
                    deleteOrderFromSharedPreferences(context )
                    deleteOrder(mOrder)
                    Log.d("cartVM--TAG", "removeProduct: delete ${mOrder?.line_items}")
                }else {
                    updateOrder(mOrder)
                    Log.d("cartVM--TAG", "removeProduct: update ${mOrder?.line_items}")
                }
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(mOrder?.let {
                    productRepository.updateOrder(it,orderId).message + e.message })
                Log.d("cartVM--TAG", "removeProduct: error ${mOrder?.line_items}")
            }
        }
    }

    fun setDiscount() {
        val mOrder = order.value?.let {
            Order(
                orderId, it.line_items , discount
            )
        }
        updateOrder(mOrder)
    }

    fun continueShopping() {
        if (!order.value?.line_items.isNullOrEmpty()) {
            val iOrder = Order(
                orderId, order.value?.line_items!!, order.value?.discount_total,
                order.value?.related_ids, customer.value?.id
            )
            updateOrder(iOrder)
        }

    }

}

