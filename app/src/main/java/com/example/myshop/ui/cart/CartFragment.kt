package com.example.myshop.ui.cart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.adapters.CartAdapter
import com.example.myshop.databinding.FragmentCartBinding
import com.example.myshop.model.LineItems
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

    }

    private fun initView() {
        vModel.getShoppingList(requireContext())
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

        val fakeList = arrayListOf(
            LineItems(0,608,"aaaaaaaaa" , 1 , "2","2","1111"),
            LineItems(1,608,"aaaaaaaaa" , 1 , "2","2","1111"),
            LineItems(2,608,"aaaaaaaaa" , 1 , "2","2","1111"),
            LineItems(3,608,"aaaaaaaaa" , 1 , "2","2","1111"),
            LineItems(4,608,"aaaaaaaaa" , 1 , "2","2","1111"),
            LineItems(5,608,"aaaaaaaaa" , 1 , "2","2","1111"),
        )
//        adapter.submitList(fakeList)


        vModel.lineItemList.observe(viewLifecycleOwner) {
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
        }
        binding.btnSetDiscount.setOnClickListener {
            setDiscount()
        }

        binding.btnContinueShopping.setOnClickListener {
            vModel.checkRegistered(requireContext())
            vModel.customer.observe(viewLifecycleOwner){
                if (it.id != null){
                    vModel.continueShopping()
                    vModel.shoppingList.postValue(null)
                    vModel.deleteOrderFromSharedPreferences(requireContext())
                    vModel.showDefaultDialog(
                        requireContext(),
                        "ثبت خرید", vModel.message.value + "خرید شما ", "متوجه شدم")
                }else{
                    vModel.showDefaultDialog(requireContext() ,"هنوز ثبت نام نکرده اید؟" ,
                        "برای ادامه خرید ثبت نام کنید یا وارد شوید." ,"باشه")
                }
            }
        }


//        vModel.state.observe(viewLifecycleOwner) {
//            when (it) {
//                State.LOADING -> {
//                    binding.recyclerCart.visibility = View.VISIBLE
//                    binding.llAllPriceCart.visibility = View.INVISIBLE
//                    binding.llDiscountCart.visibility = View.INVISIBLE
//                    binding.progressBarCart.visibility = View.VISIBLE
//                    binding.imvEmptyList.visibility = View.GONE
//                }
//                State.SUCCESS -> {
//                    binding.recyclerCart.visibility = View.VISIBLE
//                    binding.llAllPriceCart.visibility = View.VISIBLE
//                    binding.llDiscountCart.visibility = View.VISIBLE
//                    binding.progressBarCart.visibility = View.GONE
//                    binding.imvEmptyList.visibility = View.GONE
//                }
//                State.FAILED -> {
//                    binding.recyclerCart.visibility = View.VISIBLE
//                    binding.llAllPriceCart.visibility = View.INVISIBLE
//                    binding.llDiscountCart.visibility = View.INVISIBLE
//                    binding.progressBarCart.visibility = View.INVISIBLE
//                    binding.imvEmptyList.visibility = View.VISIBLE
//                }
//
//





//            }
//        }

    }

    private fun setDiscount() {
        if (binding.edtDiscountCodeCart.text.isNullOrBlank()){
            binding.edtDiscountCodeCart.error = " کد را وارد کنید! "
        }else{
            vModel.discount = binding.edtDiscountCodeCart.text.toString()
            vModel.setDiscount()
        }
    }
}