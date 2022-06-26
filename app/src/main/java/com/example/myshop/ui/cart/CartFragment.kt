package com.example.myshop.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.adapters.CartAdapter
import com.example.myshop.databinding.FragmentCartBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    val vModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cart, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility =
            View.VISIBLE
        binding.vModel = vModel
        binding.lifecycleOwner= viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        vModel.getShoppingList()

    }

    private fun initView() {
        vModel.getOrderFromSharedPreferences(requireContext())
        val adapter = CartAdapter { product , count ->
            if (count == 0)
                vModel.removeProduct(product)
            vModel.count.value = count
            vModel.calculatePrice()
        }
        binding.recyclerCart.adapter = adapter

        if (vModel.shoppingList.value.isNullOrEmpty()) {
            binding.imvEmptyList.visibility = View.VISIBLE
            binding.scrollViewCart.visibility = View.INVISIBLE
            binding.llAllPriceCart.visibility =View.INVISIBLE
        } else {

            vModel.shoppingList.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                Log.d("TAG", "initView: $it")
                binding.imvEmptyList.visibility = View.GONE
                binding.scrollViewCart.visibility = View.VISIBLE
                binding.llAllPriceCart.visibility =View.VISIBLE
                vModel.calculatePrice()

            }
        }

    }
}