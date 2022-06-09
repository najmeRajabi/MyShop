package com.example.myshop.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCategoryBinding
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.ui.adapters.CategoryAdaptor
import com.example.myshop.ui.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {


    lateinit var binding: FragmentCategoryBinding
    val vModel: CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {

        val adaptor = CategoryAdaptor{}
        binding.recyclerCategory.adapter=adaptor

        vModel.categories.observe(viewLifecycleOwner){
            adaptor.submitList(it)
        }
    }


}