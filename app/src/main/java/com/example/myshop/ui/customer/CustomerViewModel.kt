package com.example.myshop.ui.customer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Customer
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.handleRequestCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val mCustomer = MutableLiveData<Customer>()
    val registerMessage = MutableLiveData<String>()

    fun register(customer: Customer) {
        viewModelScope.launch {
            try {
                mCustomer.postValue(productRepository.register(customer = customer)[0])
                registerMessage.postValue("ثبت نام با موفقیت انجام شد. آیدی شما = "+ (mCustomer.value?.id
                    ?: 0))
            } catch (e: Exception) {
                registerMessage.postValue("ثبت نام با شکست رو برو شد !  "+ e.message)
            }
        }
    }

    fun login(id: Int , password: String) {
        viewModelScope.launch {
            try {
                val customer = productRepository.login(id)
                if (customer.password == password) {
                    mCustomer.postValue(customer)
                    registerMessage.postValue("ورود با موفقیت انجام شد. ")
                }
                registerMessage.postValue(" آیدی و یا پسورد اشتباه است!")
            } catch (e: Exception) {
                registerMessage.postValue("ورود با شکست رو برو شد ! " + e.message)


            }
        }
    }
}