package com.example.myshop.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.model.Category
import com.example.myshop.model.Image
import com.example.myshop.model.Product
import com.example.myshop.ui.adapters.HomeListsAdapter

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val vModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()


    }

    private fun initViews() {
        val lastAdapter=HomeListsAdapter{}
        val mostSeenAdapter=HomeListsAdapter{}
        val favoriteAdapter=HomeListsAdapter{}
        binding.lifecycleOwner = viewLifecycleOwner
        binding.lastAdaptor = lastAdapter
        binding.mostSeenAdaptor = mostSeenAdapter
        binding.favoriteAdaptor = favoriteAdapter

        lastAdapter.submitList(arrayListOf(
            Product(1, arrayListOf(Image(1,"",
                "https://woocommerce.s3.ir-thr-at1.arvanstorage.com/301185.jpg")),"name",
                "5454", arrayListOf()),
            Product(2, arrayListOf(Image(1,"","https://woocommerce.s3.ir-thr-at1.arvanstorage.com/301416.jpg")),"name","5454", arrayListOf()),
            Product(3, arrayListOf(Image(1,"","https://woocommerce.s3.ir-thr-at1.arvanstorage.com/301302.jpg")),"name","5454", arrayListOf()),
            Product(4, arrayListOf(Image(1,""," ")),"name","5454", arrayListOf()),
        ))
    }

}