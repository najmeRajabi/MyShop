package com.example.myshop.ui.disconnect

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myshop.data.ProductRepository
import com.example.myshop.model.Customer
import com.example.myshop.ui.customer.*
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

enum class State{ LOADING , SUCCESS , FAILED }

@HiltViewModel
open class BaseViewModel @Inject constructor():ViewModel() {

    val isConnected = MutableLiveData<Boolean>()
    val message = MutableLiveData<String>()
    var registered = false
    val customer = MutableLiveData<Customer>()
    var splashFlag = true


    fun checkRegistered(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE)
        if (!sharedPreferences.getString(CUSTOMER_NAME , "").isNullOrBlank()){
            registered = true

            val username = sharedPreferences.getString(CUSTOMER_USERNAME , "username")
//            val id = sharedPreferences.getString(CUSTOMER_ID , "-1")
            val name = sharedPreferences.getString(CUSTOMER_NAME , "name")
            val email = sharedPreferences.getString(CUSTOMER_EMAIL , "email")
            val mCustomer = Customer(id = null,email = email!!,first_name = name!!,username = username!!,password = null)
            customer.postValue(mCustomer)
        }



    }

    fun checkForInternet(context: Context){
        isConnected.value = checkForConnection(context)
    }


    fun checkForConnection(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                val network = connectivityManager.activeNetwork

                // Representation of the capabilities of an active network.
                val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

                return when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        true
                    }

                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        true
                    }

                    else -> false
                }

        }
            else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    fun lightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
    fun darkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    fun getFromSharedPref( context: Context , name: String): String? {
        val sharedPreferences =
            context.getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE)
        return sharedPreferences.getString(name , " ")
    }

    fun saveToSharedPref(context: Context,name: String, item: String){
        val sharedPreferences =
            context.getSharedPreferences(CUSTOMER_INFO, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(name, item)
        editor.apply()
    }

    fun checkTheme(context: Context){
        if (getFromSharedPref(context , THEME) == "dark"){
            darkTheme()
        }
    }
}