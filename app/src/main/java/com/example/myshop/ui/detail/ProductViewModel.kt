package com.example.myshop.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.data.Resource
import com.example.myshop.model.LineItems
import com.example.myshop.model.Order
import com.example.myshop.model.Product
import com.example.myshop.model.Review
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import com.google.android.gms.common.util.CollectionUtils.listOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
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



    fun getProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            val callback = productRepository.getProductById(id)
            product.postValue(callback.data!!)
            state.postValue(callback.status)
            message.postValue(callback.message)
            Log.d("detailVM---TAG", "getProduct related: ${product.value?.related_ids}")
            relatedIds = product.value?.related_ids

            getSameProducts()
            retrieveReview()
        }

    }

    fun createOrder(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (checkHasOrder(context) != -1) {
                Log.d("ProductVM---TAG", "createOrder---product: ${product.value}")
                if (product.value != null) {
                    val productValue = product.value
                    val order = Order(
                        0, listOf(
                            LineItems(
                                0,
                                productValue?.id!!,
                                productValue.name,
                                1,
                                productValue.price,
                                productValue.price,
                                productValue.price
                            )
                        )
                    )
                    Log.d("ProductVM---TAG", "createOrder--order: $order")
                    try {

                        var callback: Resource<Order>? = null
                        state.postValue(State.LOADING)
                        if (hasOrder) {
                            updateOrder(context, order)
                        } else {
                            callback = productRepository.createOrder(order)
                            orderCallback.postValue(callback.data!!)
                            saveOrderToSharedPreferences(context)
                            state.postValue(callback.status)
                            message.postValue(callback.message)
                            orderMessage.postValue(callback.message!!)
                        }

                    } catch (e: Exception) {
                        Log.d(
                            "ProductVM---TAG",
                            "createOrder error:${e.message}" + productRepository.createOrder(order).message
                        )
                        state.postValue(State.FAILED)
                    }
                }
            }
        }
    }

    private fun checkHasOrder(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(ORDER, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(ORDER_ID,-1)
    }

    private fun updateOrder(context: Context , order: Order) {
        var mOrder = orderCallback.value?.line_items
        val id = checkHasOrder(context)
        viewModelScope.launch {
            try {

                state.postValue(State.LOADING)
                var callback = productRepository.retrieveOrder(id)
                mOrder= (callback.data!!.line_items)
                state.postValue(callback.status)
                message.postValue(callback.message)
            } catch (e: Exception) {
            }

            if (mOrder != null &&
                product.value != null
            ) {
                val list = mOrder!!.plus(order.line_items)
                orderCallback.postValue(
                    Order(id, (list as List<LineItems>))
                )
                val callback = productRepository.updateOrder(
                    Order(id, (list as List<LineItems>)), id)
                state.postValue(callback.status)
                message.postValue(callback.message)
                orderMessage.postValue(callback.message!!)
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
        viewModelScope.launch(Dispatchers.IO) {
            var mReviews = arrayListOf<Review>()
            state.postValue(State.LOADING)
            val callback = product.value?.let {
                kotlin.collections.listOf(
                    it.id)
            }?.let { productRepository.retrieveReview(it) }
            if (callback != null) {
                for (review in callback.data!!) {
                    if (review.product_id == product.value?.id) {
                        mReviews.add(review)
                    }
                }
                reviews.postValue(mReviews)
                state.postValue(callback.status)
                message.postValue(callback.message)
            }
        }

    }

    fun getSameProducts() {
        viewModelScope.launch {
            try {
                state.postValue(State.LOADING)
                var callback: Resource<Product>? = null
                var sameList = arrayListOf<Product>()
                if (!relatedIds.isNullOrEmpty()) {
                    for (id in relatedIds!!) {
                        callback = productRepository.getProductById(id)
                        sameList.add(callback.data!!)
                    }
                    Log.d("detailVM---TAG", "getProduct: ${sameList}")
                    sameProducts.postValue(sameList)
                    if (callback != null) {
                        message.postValue(callback.message)
                        state.postValue(callback.status)
                    }
                }
                Log.d("detailVM---TAG", "getProduct out: ${sameList}")
            } catch (e: Exception) {
                state.postValue(State.FAILED)
                Log.d("detailVM---TAG", "getProduct catch: ${e.message}")
            }
        }

    }

    fun setReview(reviewText: String) {
        val review: Review =
            Review(0, customer.value?.username ?: "user", reviewText, product.value!!.id, 3)
        viewModelScope.launch {

            try {
                state.postValue(State.LOADING)
                val callback = productRepository.createReview(review)
                state.postValue(callback.status )
                message.postValue(callback.message )
                orderMessage.postValue(callback.message!!)
            } catch (e: Exception) {

                state.postValue(State.FAILED)
                message.postValue(review.let { productRepository.createReview(it).message + e.message })
                orderMessage.postValue(e.message)
            }
        }
    }

    fun deleteReview(id: Int) {
        viewModelScope.launch {
            try {
                state.postValue(State.LOADING)
                val callback = productRepository.deleteReview(id)
                message.postValue(callback.message)
                state.postValue(callback.status)
                orderMessage.postValue(callback.message!!)
            } catch (e: Exception) {
                state.postValue(State.FAILED)
                message.postValue(productRepository.deleteReview(id).message + e.message)
                orderMessage.postValue(e.message)
            }
        }
    }

    fun updateReview(id: Int, reviewText: String, review: Review) {
        viewModelScope.launch {
            val mReview: Review =
                Review(review.id, review.reviewer, reviewText, product.value!!.id, 3)

            try {
                state.postValue(State.LOADING)
                val callback = productRepository.updateReview(id, mReview)
                message.postValue(callback.message)
                state.postValue(callback.status)
                orderMessage.postValue(callback.message!!)
            } catch (e: Exception) {
                state.postValue(State.FAILED)
                message.postValue(productRepository.updateReview(id, mReview).message + e.message)
                orderMessage.postValue(e.message)
            }
        }
    }
}