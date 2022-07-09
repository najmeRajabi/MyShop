package com.example.myshop.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Order
import com.example.myshop.model.Product
import com.example.myshop.model.Review
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val ORDER = "order"
const val ORDER_ID = "orderId"
const val HAS_ORDER = "hasOrder"

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val product = MutableLiveData<Product>()
    val orderMessage = MutableLiveData<String>()
    val orderCallback = MutableLiveData<Order>()
    val reviews = MutableLiveData<List<Review>>()
    val sameProducts = MutableLiveData<List<Product>>()
    val state = MutableLiveData<State>()
    var relatedIds: List<Int>? = null
    var hasOrder = false

    init {
    }


    fun getProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            product.postValue(productRepository.getProductById(id).data!!)
            state.postValue(productRepository.getProductById(id).status)
            message.postValue(productRepository.getProductById(id).message)
            Log.d("detailVM---TAG", "getProduct related: ${product.value?.related_ids}")
            relatedIds = product.value?.related_ids

            getSameProducts()
            retrieveReview()
        }

    }

    fun createOrder(context: Context) {
        checkHasOrder(context)
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("ProductVM---TAG", "createOrder---product: ${product.value}")
            val order = Order(0, listOf(product.value!!))
            Log.d("ProductVM---TAG", "createOrder--order: $order")
            try {

                state.postValue(State.LOADING)
                if (hasOrder) {
                    updateOrder(context)

                } else {
                    orderCallback.postValue(productRepository.createOrder(order).data!![1])
                }
                state.postValue(productRepository.createOrder(order).status)
                message.postValue(productRepository.createOrder(order).message)
                orderMessage.postValue(message.value)
            } catch (e: Exception) {
                Log.d(
                    "ProductVM---TAG",
                    "createOrder error:${e.message}" + productRepository.createOrder(order).message
                )
                state.postValue(State.FAILED)
            }
        }
    }

    private fun checkHasOrder(context: Context) {
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean(HAS_ORDER, false)) {
            hasOrder = true
        }
    }

    private fun updateOrder(context: Context) {
        var order = orderCallback.value
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt(ORDER_ID, -1)
        viewModelScope.launch {
            try {

                state.postValue(State.LOADING)
                order = (productRepository.retrieveOrder(id).data!![0])
                state.postValue(productRepository.retrieveOrder(id).status)
                message.postValue(productRepository.retrieveOrder(id).message)
            } catch (e: Exception) {
            }

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
            editor.putBoolean(HAS_ORDER, true)
            hasOrder = true
            editor.apply()
        }
    }




    fun retrieveReview() {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            reviews.postValue(product.value?.let { productRepository.retrieveReview(listOf(it.id)).data }!!)
            state.postValue(productRepository.retrieveReview(listOf(product.value!!.id)).status)
            message.postValue(productRepository.retrieveReview(listOf(product.value!!.id)).message)
        }

    }

    fun getSameProducts() {
        viewModelScope.launch {
            try {
                state.postValue(State.LOADING)
                var sameList = arrayListOf<Product>()
                if (!relatedIds.isNullOrEmpty()) {
                    for (id in relatedIds!!) {
                        sameList.add(productRepository.getProductById(id).data!!)
                    }
                    Log.d("detailVM---TAG", "getProduct: ${sameList}")
                    sameProducts.postValue(sameList)
                    message.postValue(productRepository.getProductById(relatedIds!![0]).message)
                    state.postValue(productRepository.getProductById(relatedIds!![0]).status)
                }
                Log.d("detailVM---TAG", "getProduct out: ${sameList}")
            } catch (e: Exception) {
                state.postValue(State.FAILED)
                Log.d("detailVM---TAG", "getProduct catch: ${e.message}")
            }
        }

    }

    fun setReview(reviewText: String) {
        val review: Review = Review(0, customer.value?.username ?: "user", reviewText, product.value!!.id, 3)
        viewModelScope.launch {

            try {
                state.postValue(State.LOADING)
                productRepository.createReview(review)
                state.postValue(review?.let { productRepository.createReview(it).status })
                message.postValue(review?.let { productRepository.createReview(it).message })
            } catch (e: Exception) {

                state.postValue(State.FAILED)
//                message.postValue(review?.let { productRepository.createReview(it).message + e.message })
            }
        }
    }

    fun deleteReview(id: Int){
        viewModelScope.launch {
            try {
                state.postValue(State.LOADING)
                productRepository.deleteReview(id)
                message.postValue(productRepository.deleteReview(id).message)
                state.postValue(productRepository.deleteReview(id).status)
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(productRepository.deleteReview(id).message + e.message)
            }
        }
    }

    fun updateReview(id: Int,reviewText: String , review: Review){
        viewModelScope.launch {
            val mReview: Review = Review(review.id, review.reviewer, reviewText, product.value!!.id, 3)

            try {
                state.postValue(State.LOADING)
                productRepository.updateReview(id, mReview)
                message.postValue(productRepository.updateReview(id, mReview).message)
                state.postValue(productRepository.updateReview(id, mReview).status)
            }catch (e: Exception){
                state.postValue(State.FAILED)
                message.postValue(productRepository.updateReview(id, mReview).message + e.message)
            }
        }
    }
}