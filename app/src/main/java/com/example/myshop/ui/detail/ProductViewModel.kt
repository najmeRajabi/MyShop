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
import com.example.myshop.model.Review
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import java.lang.Exception
import javax.inject.Inject

const val ORDER = "order"
const val ORDER_ID = "orderId"

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val product = MutableLiveData<Product>()
    val orderMessage = MutableLiveData<String>()
    val orderCallback = MutableLiveData<Order>()
    val reviews = MutableLiveData<List<Review>>()
    val state = MutableLiveData<State>()
    var hasOrder = false


    fun getProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            product.postValue (productRepository.getProductById(id).data!!)
            state.postValue(productRepository.getProductById(id).status)
            message.postValue(productRepository.getProductById(id).message)
        }
        retrieveReview()
    }

    fun createOrder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG", "createOrder: ${product.value}")
            val order = Order(0, listOf(product.value!!))
            Log.d("TAG", "createOrder: $order")
            state.postValue(State.LOADING)
            if (hasOrder) {
                updateOrder(context)

            } else {
                orderCallback.postValue(productRepository.createOrder(order).data!!)
            }
            state.postValue(productRepository.createOrder(order).status)
            message.postValue(productRepository.createOrder(order).message)
            orderMessage.postValue(message.value)
        }
    }

    private fun updateOrder(context: Context) {
        var order = orderCallback.value
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt(ORDER_ID, -1)
        viewModelScope.launch {

            state.postValue(State.LOADING)
            order = (productRepository.retrieveOrder(id).data!![0])
            state.postValue(productRepository.retrieveOrder(id).status)
            message.postValue(productRepository.retrieveOrder(id).message)

            if (order != null &&
                product.value != null
            ) {
                val list = order!!.line_items.plus(product.value)
                orderCallback.postValue(
                    Order(
                        order!!.id,
                        (list as List<Product>)
                    )
                )
                productRepository.updateOrder(order!!, id)
            }
        }
    }

    @SuppressLint("CommitPrefEdits")
    fun saveOrderToSharedPreferences(context: Context) {
        if (orderCallback.value != null) {
            val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt(ORDER_ID, orderCallback.value!!.id)
            hasOrder = true
            editor.apply()
        }
    }


    fun retrieveReview() {
        viewModelScope.launch {
            try {
                var mReviews = arrayListOf<Review>()
                for (review in productRepository.retrieveReview()) {
                    if (review.product_id == product.value?.id) {
                        mReviews.add(review)
                    }
                }
                reviews.postValue(mReviews)
//                reviews.postValue(productRepository.retrieveReview())
            } catch (e: Exception) {

            }
        }

    }
}