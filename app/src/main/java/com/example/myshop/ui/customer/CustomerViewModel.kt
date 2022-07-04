package com.example.myshop.ui.customer

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Customer
import com.example.myshop.ui.detail.ORDER
import com.example.myshop.ui.detail.ORDER_ID
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

    fun register(iCustomer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(State.LOADING)
                val listCustomer =productRepository.register(iCustomer).data!!
                mCustomer.postValue(listCustomer[listCustomer.size-1])
                state.postValue(productRepository.register(iCustomer).status)
                message.postValue(productRepository.register(iCustomer).message)
                registerMessage.postValue(message.value + " آیدی شما:  " + mCustomer.value?.id)
                customer.postValue(mCustomer.value)
                registered = true
            }catch (e: Exception){
                state.postValue(State.FAILED)
            }

        }
    }

    fun saveCustomerToShearedPreferences(context: Context) {
        if (registered) {
            val sharedPreferences =
                context.getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString(CUSTOMER_NAME, mCustomer.value?.first_name)
            editor.putString(CUSTOMER_ID, mCustomer.value?.id)
            editor.putString(CUSTOMER_USERNAME, mCustomer.value?.username)
            editor.putString(CUSTOMER_PASSWORD, mCustomer.value?.password)
            editor.putString(CUSTOMER_EMAIL, mCustomer.value?.email)
            editor.apply()
        }
    }

    fun login(id: Int , password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                state.postValue(State.LOADING)
                val customer: Customer = productRepository.login(id).data!![0]
                state.postValue(productRepository.login(id).status)
                message.postValue(productRepository.login(id).message)
                if (customer.password.toString() == password) {
                    mCustomer.postValue(customer)
                    registerMessage.postValue(message.value + "ورود با موفقیت انجام شد. ")
                    registered = true
                } else
                    registerMessage.postValue(message.value)
            }catch (e: Exception){
                state.postValue(State.FAILED)
            }
        }
    }
}