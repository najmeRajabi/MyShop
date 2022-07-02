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
import com.example.myshop.model.Order
import com.example.myshop.model.Product
import com.example.myshop.ui.disconnect.State
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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        vModel.getShoppingList()

    }

    private fun initView() {
        vModel.getOrderFromSharedPreferences(requireContext())
        val ordersCount = arrayListOf<Int>()
        val adapter = CartAdapter { product, count , position ->
            if (count == 0)
                vModel.removeProduct(product , requireContext())
            else
                ordersCount[position]=count
            vModel.count.value = ordersCount
            vModel.calculatePrice()
        }

        binding.recyclerCart.adapter = adapter

        vModel.shoppingList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            Log.d("TAG", "initView: $it")
            vModel.calculatePrice()

            if (!it.isNullOrEmpty()&&
                it.size != ordersCount.size) {
                for (i in it.indices) {
                    ordersCount.add(1)
                }
            }
            vModel.count.value = ordersCount
            vModel.calculatePrice()
            binding.btnSetDiscount.setOnClickListener {
                setDiscount()
            }

        }


        vModel.state.observe(viewLifecycleOwner) {
            when (it) {
                State.LOADING -> {
//                    binding.scrollViewCart.visibility = View.INVISIBLE
                    binding.llAllPriceCart.visibility = View.INVISIBLE
                    binding.llDiscountCart.visibility = View.INVISIBLE
                    binding.progressBarCart.visibility = View.VISIBLE
                    binding.imvEmptyList.visibility = View.GONE
                }
                State.SUCCESS -> {
//                    binding.scrollViewCart.visibility = View.VISIBLE
                    binding.llAllPriceCart.visibility = View.VISIBLE
                    binding.llDiscountCart.visibility = View.VISIBLE
                    binding.progressBarCart.visibility = View.GONE
                    binding.imvEmptyList.visibility = View.GONE
                }
                State.FAILED -> {
                    binding.recyclerCart.visibility = View.INVISIBLE
                    binding.llAllPriceCart.visibility = View.INVISIBLE
                    binding.llDiscountCart.visibility = View.INVISIBLE
                    binding.progressBarCart.visibility = View.INVISIBLE
                    binding.imvEmptyList.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setDiscount() {
        if (binding.edtDiscountCodeCart.text.isNullOrBlank()){
            binding.edtDiscountCodeCart.error = " کد را وارد کنید! "
        }else{
            vModel.discount = binding.edtDiscountCodeCart.text.toString()
            vModel.setDiscount()
//            val order = vModel.order.value?.let {
//                Order(
//                    vModel.orderId, it.line_items ,
//                )
//            }
//            vModel.updateOrder(Order())
        }
    }
}