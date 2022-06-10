package com.example.myshop.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myshop.R
import com.example.myshop.databinding.FragmentListBinding
import com.example.myshop.databinding.FragmentProductDetailBinding
import com.example.myshop.ui.adapters.HomeListsAdapter
import com.example.myshop.ui.detail.ProductDetailFragmentArgs
import com.example.myshop.ui.detail.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val args: ListFragmentArgs by navArgs()
    lateinit var binding: FragmentListBinding
    val vModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val categoryId = args.categoryArg
        vModel.getProductList(categoryId)

        val adapter = HomeListsAdapter{}
        binding.recyclerList.adapter= adapter

        vModel.productList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }

}