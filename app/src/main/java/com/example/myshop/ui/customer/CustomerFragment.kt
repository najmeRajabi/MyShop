package com.example.myshop.ui.customer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCustomerBinding
import com.example.myshop.model.Customer
import com.example.myshop.ui.disconnect.State
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerFragment : Fragment() {

    lateinit var binding: FragmentCustomerBinding
    val vModel: CustomerViewModel by activityViewModels()
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

        checkRegister()
        initViews()
    }

    private fun checkRegister() {
        if (vModel.checkRegistered(requireContext()) == true){
            findNavController().navigate(R.id.action_customerFragment_to_customerRegisteredFragment)
        }
//        vModel.registered.observe(viewLifecycleOwner) {
//            if (it) {
//                findNavController().navigate(R.id.action_customerFragment_to_customerRegisteredFragment)
//            }
//        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initViews() {
        observeState()
        binding.btnRegister.setOnClickListener {
            if (checkedRegisterField()) {

                vModel.register(
                    Customer(
                        null,
                        binding.emailEdtRegister.text.toString(),
                        binding.nameEdtRegister.text.toString(),
                        binding.usernameEdtRegister.text.toString(),
                        binding.passwordEdtRegister.text.toString(),
                    ),requireContext()
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
                    ,requireContext()
                )
            }
        }
        binding.btnRegisterCard.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            binding.llRegister.visibility = View.VISIBLE
            binding.llSignin.visibility = View.GONE
            binding.btnRegisterCard.textSize = 18f
            binding.btnSigninCard.textSize = 12f
        }
        binding.btnSignIn.isEnabled = false
        binding.btnSigninCard.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            binding.llRegister.visibility = View.GONE
            binding.llSignin.visibility = View.VISIBLE
            binding.btnSigninCard.textSize = 18f
            binding.btnRegisterCard.textSize = 12f
        }
        vModel.registerMessage.observe(viewLifecycleOwner){
            binding.txvCustomerMessage.text = it
        }
    }

    private fun checkedRegisterField(): Boolean {
        var result = false
        when {
            binding.nameEdtRegister.text.isNullOrBlank() -> {
                binding.nameEdtFieldRegister.error = getString(R.string.emptyField)
            }
            binding.usernameEdtRegister.text.isNullOrBlank() -> {
                binding.usernameEdtFieldRegister.error = getString(R.string.emptyField)
            }
            binding.emailEdtRegister.text.isNullOrBlank() -> {
                binding.emailEdtFieldRegister.error = getString(R.string.emptyField)
            }
            binding.passwordEdtRegister.text.isNullOrBlank() -> {
                binding.passwordEdtFieldRegister.error = getString(R.string.emptyField)
            }
            else -> result = true
        }
       return  result
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
                    findNavController().navigate(R.id.action_customerFragment_to_customerRegisteredFragment)
                }
                State.FAILED -> {
                    binding.txvCustomerMessage.visibility = View.VISIBLE
                    binding.progressBarProfile.visibility = View.INVISIBLE
                }
            }
        }
    }
}