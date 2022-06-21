package com.example.myshop.ui.cart

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.adapters.CartAdapter
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.ui.detail.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : Fragment() {

    lateinit var binding: FragmentCartBinding
    val vModel : ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_cart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        val adapter = CartAdapter{
            //todo delete from list
        }
        binding.recyclerCart.adapter = adapter

        vModel.shoppingProduct.observe(viewLifecycleOwner){
//            if (it.isNullOrEmpty()){
//                binding.imvEmptyList.visibility = View.VISIBLE
//                binding.scrollViewCart.visibility = View.INVISIBLE
//            }else{
                adapter.submitList(it)
            Log.d("TAG", "initView: $it")
                binding.imvEmptyList.visibility = View.GONE
                binding.scrollViewCart.visibility = View.VISIBLE
//            }
        }

    }
}