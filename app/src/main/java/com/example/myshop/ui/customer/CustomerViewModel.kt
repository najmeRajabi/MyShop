package com.example.myshop.ui.customer

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Customer
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val CUSTOMER_INFO = "customerInfo"
const val CUSTOMER_NAME = "customerName"
const val CUSTOMER_ID = "customerId"
const val CUSTOMER_USERNAME = "customerUserName"
const val CUSTOMER_PASSWORD = "customerPassword"
const val CUSTOMER_EMAIL = "customerEmail"
const val CUSTOMER_REGISTERED = "customerRegistered"
const val THEME = "theme"

@HiltViewModel
class CustomerViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val mCustomer = MutableLiveData<Customer>()
    val registerMessage = MutableLiveData<String>()
    val state = MutableLiveData<State>()

    fun register(iCustomer: Customer, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(State.LOADING)
                val job = launch {
                    mCustomer.postValue(productRepository.register(iCustomer).data!![2])
                }
                job.join()

                Log.d("list---object", "register : ${productRepository.register(iCustomer).data!!}")
                state.postValue(productRepository.register(iCustomer).status)
                message.postValue(productRepository.register(iCustomer).message)
                registerMessage.postValue(message.value + " آیدی شما:  " + mCustomer.value?.id)
                registered.postValue(true)
                saveCustomerToShearedPreferences(context)
                customer.postValue(iCustomer)
            } catch (e: Exception) {
                state.postValue(State.FAILED)
                registerMessage.postValue(message.value + e.message)
            }

        }
    }

    fun saveCustomerToShearedPreferences(context: Context) {
        if (registered.value == true) {
            customer.value?.first_name?.let { saveToSharedPref(context, CUSTOMER_NAME, it) }
            saveToSharedPref(context, CUSTOMER_ID, mCustomer.value?.id.toString())
            customer.value?.username?.let { saveToSharedPref(context, CUSTOMER_USERNAME, it) }
            customer.value?.password?.let { saveToSharedPref(context, CUSTOMER_PASSWORD, it) }
            customer.value?.email?.let { saveToSharedPref(context, CUSTOMER_EMAIL, it) }
            saveToSharedPref(context, CUSTOMER_REGISTERED, "true")

        }
    }

        fun login(id: Int, password: String, context: Context) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    state.postValue(State.LOADING)
                    val iCustomer: Customer = productRepository.login(id).data!!
                    state.postValue(productRepository.login(id).status)
                    message.postValue(productRepository.login(id).message)
                    if (iCustomer.password.toString() == password) {
                        mCustomer.postValue(iCustomer)
                        registerMessage.postValue(message.value + "ورود با موفقیت انجام شد. ")
                        customer.postValue(mCustomer.value)
                        registered.value = true
                        saveCustomerToShearedPreferences(context)
                    } else
                        registerMessage.postValue(message.value)
                } catch (e: Exception) {
                    state.postValue(State.FAILED)
                }
            }
        }

}