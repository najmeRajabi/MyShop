package com.example.myshop.ui.customer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Customer
import com.example.myshop.ui.disconnect.BaseViewModel
import com.example.myshop.ui.disconnect.State
import com.example.myshop.ui.handleRequestCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerViewModel @Inject constructor(
    val productRepository: ProductRepository
) : BaseViewModel() {

    val mCustomer = MutableLiveData<Customer>()
    val registerMessage = MutableLiveData<String>()
    val state = MutableLiveData<State>()

    fun register(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            mCustomer.postValue( productRepository.register(customer).data!![0])
            state.postValue(productRepository.register(customer).status)
            message.postValue(productRepository.register(customer).message)
            registerMessage.postValue(message.value+" آیدی شما:  "+mCustomer.value?.id)
        }
//        viewModelScope.launch {
//            try {
//                mCustomer.postValue(productRepository.register(customer = customer)[0])
//                registerMessage.postValue("ثبت نام با موفقیت انجام شد. آیدی شما = "+ (mCustomer.value?.id
//                    ?: 0))
//            } catch (e: Exception) {
//                registerMessage.postValue("ثبت نام با شکست رو برو شد !  "+ e.message)
//            }
//        }
    }

    fun login(id: Int , password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            state.postValue(State.LOADING)
            val customer: Customer = productRepository.login(id).data!![0]
            state.postValue(productRepository.login(id).status)
            message.postValue(productRepository.login(id).message)
            if (customer.password.toString() == password) {
                mCustomer.postValue(customer)
                registerMessage.postValue(message.value+"ورود با موفقیت انجام شد. ")
            }else
                registerMessage.postValue(message.value )
        }
    }
}