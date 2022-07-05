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

@HiltViewModel
class CustomerViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val mCustomer = MutableLiveData<Customer>()
    val registerMessage = MutableLiveData<String>()
    val state = MutableLiveData<State>()

    fun register(iCustomer: Customer , context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(State.LOADING)
                mCustomer.postValue(productRepository.register(iCustomer).data!![2])
                Log.d("list---object", "register : ${productRepository.register(iCustomer).data!!}")
                state.postValue(productRepository.register(iCustomer).status)
                message.postValue(productRepository.register(iCustomer).message)
                registerMessage.postValue(message.value + " آیدی شما:  " + mCustomer.value?.id)
                registered.value = true
                saveCustomerToShearedPreferences(context)
                customer.postValue(iCustomer)
            }catch (e: Exception){
                state.postValue(State.FAILED)
                registerMessage.postValue(message.value + e.message)
            }

        }
    }

    fun saveCustomerToShearedPreferences(context: Context) {
        if (registered.value == true) {
            val sharedPreferences =
                context.getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(CUSTOMER_NAME, customer.value?.first_name)
            editor.putString(CUSTOMER_ID, customer.value?.id.toString())
            editor.putString(CUSTOMER_USERNAME, customer.value?.username)
            editor.putString(CUSTOMER_PASSWORD, customer.value?.password)
            editor.putString(CUSTOMER_EMAIL, customer.value?.email)
            editor.apply()
        }
    }

    fun login(id: Int , password: String) {
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
                } else
                    registerMessage.postValue(message.value)
            }catch (e: Exception){
                state.postValue(State.FAILED)
            }
        }
    }
}