package com.example.myshop.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.adapters.FilterAdapter
import com.example.myshop.adapters.FilterCategoryAdapter
import com.example.myshop.databinding.FragmentFilterBinding
import com.example.myshop.databinding.FragmentSearchBinding
import com.example.myshop.model.Attribute
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    lateinit var binding: FragmentFilterBinding
    val vModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_filter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        val adapter = FilterAdapter{ option, checked ->

        }
        val adapterCategoryFilter = FilterCategoryAdapter{
            adapter.submitList(it.options)
        }

        binding.recyclerFilter.adapter = adapter
        binding.recyclerFilterName.adapter = adapterCategoryFilter
//        adapterCategoryFilter.submitList(listOf(
//            Attribute(1,"aaa", listOf("a","b","c")),
//            Attribute(2,"aj", listOf("a","b","k","b","k")),
//        ))

        vModel.attributes.observe(viewLifecycleOwner){
            adapterCategoryFilter.submitList(it)
        }
    }

}