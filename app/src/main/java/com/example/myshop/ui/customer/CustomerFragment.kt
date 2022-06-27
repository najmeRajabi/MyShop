package com.example.myshop.ui.customer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCustomerBinding
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.model.Customer
import com.example.myshop.ui.disconnect.State
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerFragment : Fragment() {

    lateinit var binding: FragmentCustomerBinding
    val vModel: CustomerViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_customer, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility = View.VISIBLE
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        observeState()
        binding.btnRegister.setOnClickListener {
            if (checked()) {

                vModel.register(
                    Customer(
                        "0",
                        binding.emailEdtRegister.text.toString(),
                        binding.passwordEdtRegister.text.toString()
                    )
                )
            }
        }
        binding.btnSignIn.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            if (!binding.idEdtSignIn.text.isNullOrBlank() &&
                    !binding.passwordEdtSignIn.text.isNullOrBlank()){
                vModel.login(
                    binding.idEdtSignIn.text.toString().toInt(),
                    binding.passwordEdtSignIn.text.toString()
                )
            }
        }
        binding.btnRegisterCard.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            binding.cardRegister.visibility = View.VISIBLE
            binding.cardSignin.visibility = View.GONE
        }
        binding.btnSigninCard.setOnClickListener {
            binding.cardRegister.visibility = View.GONE
            binding.cardSignin.visibility = View.VISIBLE
        }
        vModel.registerMessage.observe(viewLifecycleOwner){
            binding.txvCustomerMessage.text = it
        }
    }

    private fun checked(): Boolean {
       return  (!binding.emailEdtRegister.text.isNullOrBlank() &&
               !binding.passwordEdtRegister.text.isNullOrBlank())
    }

    private fun observeState() {
        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> {
                    binding.txvCustomerMessage.visibility = View.INVISIBLE
                    binding.progressBarProfile.visibility = View.VISIBLE
                }
                State.SUCCESS -> {
                    binding.txvCustomerMessage.visibility = View.VISIBLE
                    binding.progressBarProfile.visibility = View.GONE
                }
                State.FAILED -> {
                    binding.txvCustomerMessage.visibility = View.VISIBLE
                    binding.progressBarProfile.visibility = View.INVISIBLE
                }
            }
        }
    }
}