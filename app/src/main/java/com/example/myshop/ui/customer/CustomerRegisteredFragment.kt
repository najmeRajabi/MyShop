package com.example.myshop.ui.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCustomerRegistereedBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerRegisteredFragment():Fragment() {

    lateinit var binding: FragmentCustomerRegistereedBinding
    val vModel: CustomerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_customer_registereed, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility = View.VISIBLE
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        vModel.customer.observe(viewLifecycleOwner){
            binding.txvUsernameProfile.text = it.username
            binding.txvIdProfile.text = it.id
            binding.txvEmailProfile.text = it.email
        }
    }
}